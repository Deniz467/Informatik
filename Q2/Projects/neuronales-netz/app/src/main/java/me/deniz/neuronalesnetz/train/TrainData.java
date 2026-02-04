package me.deniz.neuronalesnetz.train;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.io.FastByteArrayInputStream;
import it.unimi.dsi.fastutil.io.FastByteArrayOutputStream;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import javax.imageio.ImageIO;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Stores all training data grouped by label.
 *
 * <p>This class maps each label (for example a digit from 0 to 9)
 * to a collection of images that belong to this label.</p>
 */
@NullMarked
public final class TrainData implements Serializable {

  @Serial
  private static final long serialVersionUID = 4195612405936205236L;

  /**
   * Maps a label to its corresponding training data entry.
   */
  private final Int2ObjectMap<TrainDataEntry> data = new Int2ObjectOpenHashMap<>();

  /**
   * Adds a new training data entry for the given label.
   *
   * @param label the class label (for example a digit from 0 to 9)
   * @param images a list of image entries belonging to this label
   */
  public void addEntry(int label, ObjectList<Int2ObjectMap.Entry<BufferedImage>> images) {
    data.put(label, new TrainDataEntry(images));
  }

  /**
   * Returns the training data entry for the given label.
   *
   * @param label the class label
   * @return the training data entry, or {@code null} if no entry exists
   */
  @SuppressWarnings("DataFlowIssue")
  @Nullable
  public TrainDataEntry getEntry(int label) {
    return data.get(label);
  }

  /**
   * Returns the training data entry for the given label or throws an exception
   * if no entry exists.
   *
   * @param label the class label
   * @return the training data entry
   * @throws IllegalArgumentException if no data exists for the label
   */
  public TrainDataEntry getEntryOrThrow(int label) {
    var entry = getEntry(label);
    if (entry == null) {
      throw new IllegalArgumentException("No entry for label " + label);
    }
    return entry;
  }

  @Override
  public String toString() {
    return "TrainData{" +
        "data=" + data +
        '}';
  }

  /**
   * Represents all images belonging to a single label.
   */
  public static class TrainDataEntry implements Serializable {

    @Serial
    private static final long serialVersionUID = 4645214050279627702L;

    /**
     * Maps an image index to the corresponding image.
     */
    private transient Int2ObjectMap<BufferedImage> images;


    TrainDataEntry(ObjectList<Int2ObjectMap.Entry<BufferedImage>> images) {
      this.images = new Int2ObjectOpenHashMap<>(images.size());
      images.forEach(e -> this.images.put(e.getIntKey(), e.getValue()));
    }

    /**
     * Returns all images for this label.
     *
     * @return a map of image indices to images
     */
    public Int2ObjectMap<BufferedImage> images() {
      return images;
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
      out.defaultWriteObject();
      out.writeInt(images.size());

      for (var entry : images.int2ObjectEntrySet()) {
        var key = entry.getIntKey();
        var img = entry.getValue();

        out.writeInt(key);

        var pngBytes = toPngBytes(img);
        out.writeInt(pngBytes.length);
        out.write(pngBytes);
      }
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();

      int size = in.readInt();
      this.images = new Int2ObjectOpenHashMap<>(size);

      for (int i = 0; i < size; i++) {
        int key = in.readInt();
        int length = in.readInt();

        if (length < 0) throw new IOException("Negative image byte length: " + length);

        var pngBytes = new byte[length];
        in.readFully(pngBytes);

        var img = fromPngBytes(pngBytes);
        this.images.put(key, img);
      }
    }

    private static byte[] toPngBytes(BufferedImage img) throws IOException {
      ImageIO.setUseCache(false);

      try (var out = new FastByteArrayOutputStream(32_768)) {
        boolean written = ImageIO.write(img, "png", out);
        if (!written) throw new IOException("No PNG writer available for ImageIO");
        return out.toByteArray();
      }
    }

    private static BufferedImage fromPngBytes(byte[] pngBytes) throws IOException {
      ImageIO.setUseCache(false);

      try (var bais = new FastByteArrayInputStream(pngBytes)) {
        var image = ImageIO.read(bais);
        if (image == null) throw new IOException("ImageIO.read returned null (corrupt/unknown format)");
        return image;
      }
    }

    @Override
    public String toString() {
      return "TrainDataEntry{" +
          "images=" + images +
          '}';
    }
  }
}
