package me.deniz.neuronalesnetz;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.ExecutionException;
import me.deniz.neuronalesnetz.activation.RectifiedLinearUnit;
import me.deniz.neuronalesnetz.activation.SigmoidActivationFunction;
import me.deniz.neuronalesnetz.net.Net;
import me.deniz.neuronalesnetz.net.NetSerialization;
import me.deniz.neuronalesnetz.test.NetTest;
import me.deniz.neuronalesnetz.train.NetTrain;
import me.deniz.neuronalesnetz.train.TrainDataLoader;
import org.jspecify.annotations.NullMarked;

/**
 * Main entry point of the program.
 *
 * <p>This class creates the neural network, loads training data, trains the network
 * (if it is not trained yet), saves the network to a file, and then runs a small test.</p>
 *
 * <p>The program also tries to load a previously saved network from disk. If a saved
 * network exists and it has the same input size and the same layer sizes as the
 * network configuration in this class, the saved network will be reused.</p>
 */
@NullMarked
public final class App {

  /**
   * Starts the program.
   *
   * <p>Steps:</p>
   * <ol>
   *   <li>Create a new network with the configured layer sizes</li>
   *   <li>Try to load a saved network and reuse it if it matches the configuration</li>
   *   <li>Load the training data from the ZIP resource</li>
   *   <li>Train the network (only if it is not already trained)</li>
   *   <li>Save the network to a file</li>
   *   <li>Run a test with random samples and print the accuracy</li>
   * </ol>
   *
   * @param args command line arguments (not used)
   * @throws NoSuchAlgorithmException if the strong secure random algorithm is not available
   * @throws ExecutionException if testing tasks fail
   * @throws InterruptedException if the test thread is interrupted
   */
  public static void main(String[] args)
      throws NoSuchAlgorithmException, ExecutionException, InterruptedException {
    var random = SecureRandom.getInstanceStrong();

    // Build the base network configuration (input -> hidden -> output)
    var net = Net.create(Settings.IMG_WIDTH * Settings.IMG_HEIGHT, random)
        .addLayer(128, RectifiedLinearUnit.INSTANCE)
//        .addLayer(16, PositiveActivationFunction.INSTANCE)
        .addLayer(10, SigmoidActivationFunction.INSTANCE);

    // Try to load a previously saved network from disk
    var trainedNet = NetSerialization.loadNetFromFile();

    // If a saved network exists, reuse it only if it matches the current network shape
    if (trainedNet != null) {
      if (trainedNet.getInputSize() == net.getInputSize()) {
        var trainedNetLayers = trainedNet.getLayers();
        var layers = net.getLayers();

        if (trainedNetLayers.size() == layers.size()) {
          boolean mismatchFound = false;
          for (int i = 0; i < trainedNetLayers.size(); i++) {
            var trainedLayer = trainedNetLayers.get(i);
            var layer = layers.get(i);

            if (trainedLayer.getNeuronCount() != layer.getNeuronCount()) {
              mismatchFound = true;
              break;
            }
          }
          if (!mismatchFound) {
            net = trainedNet;
          }
        }
      }
    }

    var trainData = TrainDataLoader.loadTrainData();

    if (!net.isTrained()) {
      NetTrain.trainNet(net, trainData, random);
      net.setTrained(true);
    }

    NetSerialization.saveNetToFile(net);
    System.out.println("Done");

    System.out.println("Starting testing...");
    NetTest.testNet(net, random, trainData);
  }
}
