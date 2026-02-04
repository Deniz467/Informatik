package me.deniz.neuronalesnetz.train;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class ImagePreprocess {

  private ImagePreprocess() {
    throw new UnsupportedOperationException();
  }

  public static DoubleList toInputVector(BufferedImage src, int width, int height) {
    var resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    var graphics = resized.createGraphics();
    graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    graphics.drawImage(src, 0, 0, width, height, null);
    graphics.dispose();

    // Grayscale + normalize to [0,1]
    var out = new DoubleArrayList(width * height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int rgb = resized.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF;
        int gr = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        double gray = 0.2126 * r + 0.7152 * gr + 0.0722 * b;

        out.add(gray / 255.0);
      }
    }

    return out;
  }

  public static DoubleList createTargetForDigit(int digit) {
    var target = new DoubleArrayList(10);
    for (int i = 0; i < 10; i++) {
      target.add(i == digit ? 1.0 : 0.0);
    }
    return target;
  }
}
