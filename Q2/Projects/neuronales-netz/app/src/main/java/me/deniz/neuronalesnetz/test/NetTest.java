package me.deniz.neuronalesnetz.test;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.random.RandomGenerator;
import me.deniz.neuronalesnetz.Settings;
import me.deniz.neuronalesnetz.net.Net;
import me.deniz.neuronalesnetz.train.ImagePreprocess;
import me.deniz.neuronalesnetz.train.NetTrain;
import me.deniz.neuronalesnetz.train.TrainData;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;


/**
 * Simple testing utility for the neural network.
 *
 * <p>This class evaluates the network by selecting random images from the training data,
 * running a forward pass, and comparing the predicted label with the true label.</p>
 */
public final class NetTest {

  /**
   * One test sample: a label (0-9) and the corresponding image.
   *
   * @param label the correct digit label
   * @param img the image for this label
   */
  record Sample(int label, BufferedImage img) {

  }

  /**
   * Tests the given network on random samples and prints the accuracy.
   *
   * <p>This method:</p>
   * <ol>
   *   <li>Builds a list of random samples from {@link TrainData}</li>
   *   <li>Runs the network on each sample (in parallel)</li>
   *   <li>Counts correct predictions and prints the accuracy</li>
   * </ol>
   *
   * <p>It also prints how often each digit was predicted. This helps to see
   * if the network always predicts the same digit.</p>
   *
   * @param net the neural network to test
   * @param random random generator used to pick samples
   * @param trainData the loaded training data (images grouped by label)
   * @throws InterruptedException if the test is interrupted
   */
  public static void testNet(Net net, RandomGenerator random, TrainData trainData)
      throws InterruptedException {
    // Create random test samples
    var samples = new ObjectArrayList<Sample>(Settings.TESTING_SIZE);
    for (int i = 0; i < Settings.TESTING_SIZE; i++) {
      var label = Settings.LABELS.getInt(random.nextInt(Settings.LABELS.size()));
      var entry = trainData.getEntryOrThrow(label);
      var img = NetTrain.pickRandomImage(entry.images(), random);

      if (img != null) {
        samples.add(new Sample(label, img));
      }
    }

    var executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    try (var progress = ProgressBar.builder()
        .setInitialMax(Settings.TESTING_SIZE)
        .setTaskName("Testing Net")
        .setStyle(ProgressBarStyle.COLORFUL_UNICODE_BAR)
        .build()
    ) {
      // Create one task per sample: preprocess -> feed forward -> argMax
      var tasks = new ObjectArrayList<Callable<Integer>>(Settings.TESTING_SIZE);
      for (var sample : samples) {
        tasks.add(() -> {
          var input = ImagePreprocess.toInputVector(
              sample.img(),
              Settings.IMG_WIDTH,
              Settings.IMG_HEIGHT
          );
          var out = net.feedForward(input);
          return argMax(out);
        });
      }

      int[] predCount = new int[10];
      int correct = 0;

      // Run tasks in parallel and collect results
      var futures = executorService.invokeAll(tasks);
      for (int i = 0; i < futures.size(); i++) {
        int predicted = futures.get(i).resultNow();
        predCount[predicted]++;

        int label = samples.get(i).label();
        if (predicted == label) {
          correct++;
        }

        progress.step();
      }

      System.out.println("Valid samples: " + samples.size());
      System.out.println("Pred counts: " + Arrays.toString(predCount));
      System.out.println("Accuracy: " + (correct / (double) samples.size()));
    } finally {
      executorService.shutdown();
    }
  }

  /**
   * Returns the index of the largest value in the output list.
   *
   * <p>This is used to convert the network output vector into a predicted label.
   * Example: if output[3] is the biggest value, the predicted digit is 3.</p>
   *
   * @param outputs the output values of the network
   * @return the index of the maximum value
   */
  private static int argMax(DoubleList outputs) {
    int maxIndex = 0;
    double maxValue = outputs.getDouble(0);
    for (int i = 1; i < outputs.size(); i++) {
      double value = outputs.getDouble(i);
      if (value > maxValue) {
        maxValue = value;
        maxIndex = i;
      }
    }
    return maxIndex;
  }
}
