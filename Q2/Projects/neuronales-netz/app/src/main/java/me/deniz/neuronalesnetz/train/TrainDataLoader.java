package me.deniz.neuronalesnetz.train;

import static com.google.common.base.Preconditions.checkNotNull;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.io.FastByteArrayInputStream;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.imageio.ImageIO;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Loads training images from a ZIP file located in the program resources.
 *
 * <p>The ZIP file is expected to have the following structure:</p>
 *
 * <pre>
 * train/
 *   0/
 *     0.png
 *     1.png
 *   1/
 *     0.png
 *     1.png
 *   ...
 * </pre>
 *
 * <p>Images are loaded in parallel to improve loading speed.</p>
 */
@NullMarked
public final class TrainDataLoader {

  /**
   * Path to the training data ZIP file inside the resources folder.
   */
  private static final String RESOURCE_PATH = "train.zip";

  /**
   * Loads and parses the training data from the ZIP file.
   *
   * @return the loaded training data
   * @throws RuntimeException if the ZIP file cannot be read
   */
  public static TrainData loadTrainData() {
    // label -> list of (index, image)
    var dataMap = new ConcurrentHashMap<Integer, ObjectList<Entry<BufferedImage>>>();

    var threadCount = Math.max(2, Runtime.getRuntime().availableProcessors());
    var executor = Executors.newFixedThreadPool(threadCount);

    try (var in = TrainDataLoader.class.getResourceAsStream("/" + RESOURCE_PATH)) {
      checkNotNull(in, "Train data not found");

      try (var zipIn = new ZipInputStream(in);
          var progressBar = ProgressBar.builder()
              .setTaskName("Loading train data")
              .setInitialMax(60000)
              .setStyle(ProgressBarStyle.COLORFUL_UNICODE_BAR)
              .showSpeed()
              .clearDisplayOnFinish()
              .build()
      ) {

        var tasks = new ObjectArrayList<Callable<@Nullable Void>>();
        ZipEntry zipEntry;

        while ((zipEntry = zipIn.getNextEntry()) != null) {
          if (!isValidImageEntry(zipEntry)) {
            continue;
          }

          var pathParts = zipEntry.getName().split("/");
          var label = tryParseInt(pathParts[1]);
          var imageIndex = tryParseInt(pathParts[2].replace(".png", ""));

          if (label == null || imageIndex == null) {
            continue;
          }

          byte[] imageBytes = zipIn.readAllBytes();

          // Load image asynchronously
          tasks.add(() -> {
            var image = loadImage(imageBytes);
            dataMap.compute(label, (k, v) -> {
              if (v == null) {
                v = new ObjectArrayList<>();
              }
              v.add(Int2ObjectMap.entry(imageIndex, image));
              return v;
            });

            progressBar.step();
            return null;
          });
        }

        // Wait for all tasks to finish
        executor.invokeAll(tasks);
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      executor.shutdown();
    }
    var trainData = new TrainData();
    for (var entry : dataMap.entrySet()) {
      trainData.addEntry(entry.getKey(), entry.getValue());
    }
    return trainData;
  }

  /**
   * Checks whether a ZIP entry is a valid training image.
   *
   * @param entry the ZIP entry
   * @return true if the entry represents a PNG image inside the train folder
   */
  private static boolean isValidImageEntry(ZipEntry entry) {
    if (entry.isDirectory()) {
      return false;
    }

    if (!entry.getName().endsWith(".png")) {
      return false;
    }

    var parts = entry.getName().split("/");
    return parts.length == 3 && "train".equals(parts[0]);
  }

  /**
   * Loads a PNG image from a byte array.
   *
   * @param bytes the image bytes
   * @return the loaded image
   */
  private static BufferedImage loadImage(byte[] bytes) {
    try (var input = new FastByteArrayInputStream(bytes)) {
      return ImageIO.read(input);
    } catch (IOException e) {
      throw new RuntimeException("Failed to read image", e);
    }
  }

  /**
   * Tries to parse an integer from a string.
   *
   * @param value the string value
   * @return the parsed integer, or {@code null} if parsing fails
   */
  @Nullable
  private static Integer tryParseInt(String value) {
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      return null;
    }
  }
}