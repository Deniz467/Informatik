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

@NullMarked
public final class NetTrain {

  public static void trainNet(Net net, TrainData trainData, RandomGenerator random) {
    try (var progress = ProgressBar.builder()
        .setTaskName("Training Net")
        .setStyle(ProgressBarStyle.COLORFUL_UNICODE_BAR)
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
