package me.deniz.neuronalesnetz.net;

import java.nio.file.Files;
import java.nio.file.Path;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public final class NetSerialization {
  private NetSerialization() {
    throw new UnsupportedOperationException();
  }

  private static final Path SAVE_PATH = Path.of("net_save.dat");

  public static void saveNetToFile(Net net) {
    try (var out = new java.io.ObjectOutputStream(Files.newOutputStream(SAVE_PATH))) {
      out.writeObject(net);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Nullable
  public static Net loadNetFromFile() {
    try (var in = new java.io.ObjectInputStream(Files.newInputStream(SAVE_PATH))) {
      return (Net) in.readObject();
    } catch (Exception e) {
      if (e instanceof NoSuchFieldException) {
        return null;
      }
      e.printStackTrace();
      return null;
    }
  }
}
