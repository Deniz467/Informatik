package me.deniz.neuronalesnetz.train;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
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

  public static TrainData loadTrainData() {
    return loadTrainDataFromZip();
  }

  /**
   * Loads and parses the training data from the ZIP file.
   *
   * @return the loaded training data
   * @throws RuntimeException if the ZIP file cannot be read
   */
  private static TrainData loadTrainDataFromZip() {
    // Disable disk caching for image loading to speed up the process and reduce disk I/O
    ImageIO.setUseCache(false);

    var trainDataBuilder = TrainData.builder();

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

          var image = ImageIO.read(zipIn);
          trainDataBuilder.addImage(label, imageIndex, image);

          progressBar.step();
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to read ZIP file", e);
    }

    return trainDataBuilder.build();
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