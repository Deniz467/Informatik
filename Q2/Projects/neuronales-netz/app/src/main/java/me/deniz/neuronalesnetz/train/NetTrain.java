package me.deniz.neuronalesnetz.train;

import static com.google.common.base.Preconditions.checkNotNull;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.awt.image.BufferedImage;
import java.util.random.RandomGenerator;
import me.deniz.neuronalesnetz.Settings;
import me.deniz.neuronalesnetz.net.Net;
import me.deniz.neuronalesnetz.net.ProgressListener;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Training helper for the neural network.
 *
 * <p>This class runs the training loop. It repeatedly picks a random label,
 * picks a random image for that label, converts it into an input vector,
 * creates a target vector, and then trains the network once.</p>
 */
@NullMarked
public final class NetTrain {

  /**
   * Trains the given network using the provided training data.
   *
   * <p>Training is done in multiple rounds. In each step, a random image is chosen
   * and used for one training update.</p>
   *
   * <p>The progress bar shows the number of finished rounds (not every single step),
   * because showing progress for every step would be too slow.</p>
   *
   * @param net the network that should be trained
   * @param trainData the training data (images grouped by label)
   * @param random random generator used to pick labels and images
   */
  public static void trainNet(Net net, TrainData trainData, RandomGenerator random) {
    try (var progress = ProgressBar.builder()
        .setTaskName("Training Net")
        .setStyle(ProgressBarStyle.ASCII)
        .setInitialMax(Settings.TRAINING_ROUNDS)
        .continuousUpdate()
        .build()
    ) {
      for (int trainingRound = 1; trainingRound <= Settings.TRAINING_ROUNDS; trainingRound++) {
        for (int step = 0; step < Settings.STEPS_PER_ROUND; step++) {
          // random label
          int label = Settings.LABELS.getInt(random.nextInt(Settings.LABELS.size()));

          // random image from label
          var entry = trainData.getEntryOrThrow(label);
          var img = pickRandomImage(entry.images(), random);
          if (img == null) {
            progress.step();
            continue;
          }

          // build input and target
          var input = ImagePreprocess.toInputVector(img, 28, 28);
          var target = ImagePreprocess.createTargetForDigit(label);

          // train
          int finalStep = step;
          net.trainBackprop(
              input,
              target,
              Settings.LEARNING_RATE,
//              Settings.EPSILON,
//              (current, total, msg) -> {
//                progress.setExtraMessage(
//                    "step " + (finalStep + 1) + "/" + Settings.STEPS_PER_ROUND +
//                        " label " + label
//                );
//              }
              ProgressListener.none()
          );
        }

        progress.step();
      }
    }
  }

  /**
   * Picks a random image from the given map.
   *
   * @param images map of image index -> image
   * @param random random generator used to pick the image
   * @return a random image, or {@code null} if the map is empty
   */
  @Nullable
  public static BufferedImage pickRandomImage(
      Int2ObjectMap<BufferedImage> images,
      RandomGenerator random
  ) {
    checkNotNull(random, "random");
    checkNotNull(images, "images");

    if (images.isEmpty()) {
      return null;
    }

    var keys = new IntArrayList(images.keySet());
    int k = keys.getInt(random.nextInt(keys.size()));
    return images.get(k);
  }
}
