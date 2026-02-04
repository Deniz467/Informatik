package me.deniz.neuronalesnetz.train;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.awt.image.BufferedImage;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class TrainData {

  private final Int2ObjectMap<TrainDataEntry> data = new Int2ObjectOpenHashMap<>();

  public void addEntry(int label, ObjectList<Int2ObjectMap.Entry<BufferedImage>> images) {
    data.put(label, new TrainDataEntry(images));
  }

  @Nullable
  public TrainDataEntry getEntry(int label) {
    return data.get(label);
  }

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

  public static class TrainDataEntry {

    private final Int2ObjectMap<BufferedImage> images;

    TrainDataEntry(ObjectList<Int2ObjectMap.Entry<BufferedImage>> images) {
      this.images = new Int2ObjectOpenHashMap<>(images.size());
      images.forEach(e -> this.images.put(e.getIntKey(), e.getValue()));
    }

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
