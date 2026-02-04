package me.deniz.neuronalesnetz.net;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Utility class for saving and loading a neural network to and from a file.
 *
 * <p>This class uses Java object serialization to store a trained {@link Net}
 * on disk and load it again later. This makes it possible to reuse a trained
 * network without training it again every time the program is started.</p>
 */
@NullMarked
public final class NetSerialization {

  private NetSerialization() {
    throw new UnsupportedOperationException();
  }

  /**
   * File path where the network is saved.
   */
  private static final Path SAVE_PATH = Path.of("net_save.dat");

  /**
   * Saves the given neural network to a file.
   *
   * @param net the neural network to save
   */
  public static void saveNetToFile(Net net) {
    try (var out = new java.io.ObjectOutputStream(Files.newOutputStream(SAVE_PATH))) {
      out.writeObject(net);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads a neural network from the save file.
   *
   * <p>If the save file does not exist, this method returns {@code null}.
   * If another error occurs (for example, a corrupted file), the error is
   * printed and {@code null} is returned.</p>
   *
   * @return the loaded neural network, or {@code null} if no saved network exists
   */
  @Nullable
  public static Net loadNetFromFile() {
    try (var in = new java.io.ObjectInputStream(Files.newInputStream(SAVE_PATH))) {
      return (Net) in.readObject();
    } catch (Exception e) {
      if (e instanceof NoSuchFileException) {
        return null;
      }
      e.printStackTrace();
      return null;
    }
  }
}
