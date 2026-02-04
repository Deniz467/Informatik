package me.deniz.neuronalesnetz;

import it.unimi.dsi.fastutil.ints.IntList;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.ExecutionException;
import me.deniz.neuronalesnetz.activation.PositiveActivationFunction;
import me.deniz.neuronalesnetz.activation.SigmoidActivationFunction;
import me.deniz.neuronalesnetz.net.Net;
import me.deniz.neuronalesnetz.net.NetSerialization;
import me.deniz.neuronalesnetz.test.NetTest;
import me.deniz.neuronalesnetz.train.NetTrain;
import me.deniz.neuronalesnetz.train.TrainDataLoader;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class App {

  public static void main(String[] args)
      throws NoSuchAlgorithmException, ExecutionException, InterruptedException {
    var random = SecureRandom.getInstanceStrong();
    var net = Net.create(Settings.IMG_WIDTH * Settings.IMG_HEIGHT, random)
        .addLayer(128, PositiveActivationFunction.INSTANCE)
//        .addLayer(16, PositiveActivationFunction.INSTANCE)
        .addLayer(10, SigmoidActivationFunction.INSTANCE);

    var trainedNet = NetSerialization.loadNetFromFile();

    // check if net sizes are same
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
