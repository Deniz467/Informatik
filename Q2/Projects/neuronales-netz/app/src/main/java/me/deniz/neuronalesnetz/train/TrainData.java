package me.deniz.neuronalesnetz.train;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.awt.image.BufferedImage;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Stores all training data grouped by label.
 *
 * <p>This class maps each label (for example a digit from 0 to 9)
 * to a collection of images that belong to this label.</p>
 */
@NullMarked
public final class TrainData {

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
  public static class TrainDataEntry {

    /**
     * Maps an image index to the corresponding image.
     */
    private final Int2ObjectMap<BufferedImage> images;


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

    @Override
    public String toString() {
      return "TrainDataEntry{" +
          "images=" + images +
          '}';
    }
  }
}
