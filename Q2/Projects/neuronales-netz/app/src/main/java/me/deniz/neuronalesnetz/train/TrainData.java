package me.deniz.neuronalesnetz.train;

import com.google.common.base.MoreObjects;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.awt.image.BufferedImage;
import org.jetbrains.annotations.Unmodifiable;
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

  private final Int2ObjectMap<TrainDataEntry> data;

  private TrainData(Int2ObjectMap<TrainDataEntry> data) {
    this.data = Int2ObjectMaps.unmodifiable(new Int2ObjectOpenHashMap<>(data));
  }

  public static Builder builder() {
    return new Builder();
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
   * Returns the training data entry for the given label or throws an exception if no entry exists.
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

  @Unmodifiable
  public Int2ObjectMap<TrainDataEntry> entries() {
    return data;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("data", data)
        .toString();
  }

  /**
   * Represents all images belonging to a single label.
   *
   * @param images a map of image indices to images
   */
  public record TrainDataEntry(@Unmodifiable Int2ObjectMap<BufferedImage> images) {

    public TrainDataEntry(Int2ObjectMap<BufferedImage> images) {
      this.images = Int2ObjectMaps.unmodifiable(new Int2ObjectOpenHashMap<>(images));
    }
  }

  @NullMarked
  public static final class Builder {

    private final Int2ObjectMap<Int2ObjectMap<BufferedImage>> data = new Int2ObjectOpenHashMap<>();

    private Builder() {
    }

    public Builder setEntries(int label, Int2ObjectMap<BufferedImage> images) {
      data.put(label, new Int2ObjectOpenHashMap<>(images));
      return this;
    }

    public Builder addImage(int label, int index, BufferedImage image) {
      data.computeIfAbsent(label, k -> new Int2ObjectOpenHashMap<>()).put(index, image);
      return this;
    }

    public TrainData build() {
      var built = new Int2ObjectOpenHashMap<TrainDataEntry>(data.size());
      for (var e : data.int2ObjectEntrySet()) {
        built.put(e.getIntKey(), new TrainDataEntry(e.getValue()));
      }
      return new TrainData(built);
    }
  }

}
