package me.deniz.neuronalesnetz.train;

import static com.google.common.base.Preconditions.checkNotNull;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.imageio.ImageIO;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public final class TrainDataLoader {

  private static final String RESOURCE_PATH = "train.zip";

  public static TrainData loadTrainData() {
    var dataMap = new Int2ObjectOpenHashMap<ObjectList<Int2ObjectMap.Entry<BufferedImage>>>();
    int threads = Math.max(2, Runtime.getRuntime().availableProcessors());
    var executor = Executors.newFixedThreadPool(threads);
    try (var in = TrainDataLoader.class.getResourceAsStream("/" + RESOURCE_PATH)) {
      checkNotNull(in, "Train data not found");
      try (var zipIn = new ZipInputStream(in); var pb = ProgressBar.builder()
          .setTaskName("Loading train data").setInitialMax(60000)
          .setStyle(ProgressBarStyle.COLORFUL_UNICODE_BAR).showSpeed().clearDisplayOnFinish()
          .build()) {
        var futures = new ArrayList<CompletableFuture<Void>>();
        ZipEntry entry;
        while ((entry = zipIn.getNextEntry()) != null) {
          if (entry.isDirectory() || !entry.getName().endsWith(".png")) {
            continue;
          }
          var parts = entry.getName().split("/");
          if (parts.length != 3 || !"train".equals(parts[0])) {
            continue;
          }
          var label = tryParseInt(parts[1]);
          var index = tryParseInt(parts[2].replace(".png", ""));
          if (label == null || index == null) {
            continue;
          }
          byte[] pngBytes = zipIn.readAllBytes();
          var future = CompletableFuture.supplyAsync(() -> {
            try (var bais = new java.io.ByteArrayInputStream(pngBytes)) {
              return ImageIO.read(bais);
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          }, executor).thenAccept(img -> {
            synchronized (dataMap) {
              dataMap.computeIfAbsent(label, k -> new ObjectArrayList<>())
                  .add(Int2ObjectMap.entry(index, img));
            }
            pb.step();
          });
          futures.add(future);
        }
        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      executor.shutdown();
    }
    var trainData = new TrainData();
    for (var entry : dataMap.int2ObjectEntrySet()) {
      trainData.addEntry(entry.getIntKey(), entry.getValue());
    }
    return trainData;
  }

  @Nullable
  private static Integer tryParseInt(String s) {
    try {
      return Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return null;
    }
  }
}