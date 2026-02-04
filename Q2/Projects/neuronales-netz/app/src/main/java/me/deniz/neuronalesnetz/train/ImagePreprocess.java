package me.deniz.neuronalesnetz.train;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import org.jspecify.annotations.NullMarked;


/**
 * Utility class for preparing images and targets for the neural network.
 *
 * <p>This class converts images into numeric input vectors and creates
 * target vectors for digit classification. These vectors are then used
 * during training and testing.</p>
 */
@NullMarked
public final class ImagePreprocess {

  private ImagePreprocess() {
    throw new UnsupportedOperationException();
  }

  /**
   * Converts an image into a normalized input vector for the neural network.
   *
   * <p>The image is first resized to the given width and height.
   * Then it is converted to grayscale and each pixel value is normalized
   * to the range {@code [0, 1]}.</p>
   *
   * <p>The resulting vector has {@code width * height} values and can be
   * directly used as input for the network.</p>
   *
   * @param src the source image
   * @param width the target image width
   * @param height the target image height
   * @return a list of normalized pixel values
   */
  public static DoubleList toInputVector(BufferedImage src, int width, int height) {
    // Create a new image with the desired size
    // TYPE_INT_RGB means each pixel is stored as a single integer (RGB) with no alpha channel
    var resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    // Source - https://stackoverflow.com/a/4216315
    // Posted by charisis, modified by community. See post 'Timeline' for change history
    // Retrieved 2026-02-04, License - CC BY-SA 4.0
    var graphics = resized.createGraphics();
    graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    graphics.drawImage(src, 0, 0, width, height, null);
    graphics.dispose();

    // Convert to grayscale and normalize values to [0, 1]
    var out = new DoubleArrayList(width * height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        // https://codingtechroom.com/question/java-bufferedimage-getrgb-output-values
        int rgb = resized.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        // https://en.wikipedia.org/wiki/Grayscale#Converting_color_to_grayscale
        double gray = 0.2126 * red + 0.7152 * green + 0.0722 * blue;

        // Normalize to [0, 1]
        out.add(gray / 255.0);
      }
    }

    return out;
  }

  /**
   * Creates a target vector for digit classification.
   *
   * <p>The index of the correct digit is set to {@code 1.0},
   * all other values are {@code 0.0}.</p>
   *
   * <p>Example for digit 3:</p>
   * <pre>
   * [0, 0, 0, 1, 0, 0, 0, 0, 0, 0]
   * </pre>
   *
   * @param digit the digit label (0 to 9)
   * @return a target vector for the given digit
   */
  public static DoubleList createTargetForDigit(int digit) {
    var target = new DoubleArrayList(10);
    for (int i = 0; i < 10; i++) {
      target.add(i == digit ? 1.0 : 0.0);
    }
    return target;
  }
}
