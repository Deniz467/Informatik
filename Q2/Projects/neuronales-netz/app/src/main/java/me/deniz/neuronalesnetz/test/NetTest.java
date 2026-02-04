package me.deniz.neuronalesnetz.test;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.random.RandomGenerator;
import me.deniz.neuronalesnetz.Settings;
import me.deniz.neuronalesnetz.net.Net;
import me.deniz.neuronalesnetz.train.ImagePreprocess;
import me.deniz.neuronalesnetz.train.NetTrain;
import me.deniz.neuronalesnetz.train.TrainData;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;

public final class NetTest {

  record Sample(int label, BufferedImage img) {

  }

  public static void testNet(Net net, RandomGenerator random, TrainData trainData)
      throws InterruptedException, ExecutionException {
    var samples = new ObjectArrayList<Sample>(Settings.TESTING_SIZE);
    for (int i = 0; i < Settings.TESTING_SIZE; i++) {
      int label = Settings.LABELS.getInt(random.nextInt(Settings.LABELS.size()));
      var entry = trainData.getEntryOrThrow(label);
      BufferedImage img = NetTrain.pickRandomImage(entry.images(), random);
      if (img != null) {
        samples.add(new Sample(label, img));
      }
    }

    ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    try (var progress = ProgressBar.builder()
        .setInitialMax(Settings.TESTING_SIZE)
        .setTaskName("Testing Net")
        .setStyle(ProgressBarStyle.COLORFUL_UNICODE_BAR)
        .build()
    ) {
      ObjectList<Callable<Integer>> tasks = new ObjectArrayList<>(Settings.TESTING_SIZE);
      for (var sample : samples) {
        tasks.add(() -> {
          var input = ImagePreprocess.toInputVector(sample.img(), Settings.IMG_WIDTH,
              Settings.IMG_HEIGHT);
          var out = net.feedForward(input);
          return argMaxSafe(out);
        });
      }

      int[] predCount = new int[10];
      int correct = 0;

      var futures = exec.invokeAll(tasks);
      for (int i = 0; i < futures.size(); i++) {
        int predicted = futures.get(i).get();
        predCount[predicted]++;

        int label = samples.get(i).label();
        if (predicted == label) correct++;

        progress.step();
      }

      System.out.println("Valid samples: " + samples.size());
      System.out.println("Pred counts: " + Arrays.toString(predCount));
      System.out.println("Accuracy: " + (correct / (double) samples.size()));
    } finally {
      exec.shutdown();
    }
  }

  /**
   * Returns index of maximum value in list
   */
  private static int argMaxSafe(DoubleList outputs) {
    int best = 0;
    double bestVal = Double.NEGATIVE_INFINITY;

    for (int i = 0; i < outputs.size(); i++) {
      double v = outputs.getDouble(i);
      if (Double.isNaN(v)) continue;
      if (v > bestVal) {
        bestVal = v;
        best = i;
      }
    }
    return best;
  }


}
