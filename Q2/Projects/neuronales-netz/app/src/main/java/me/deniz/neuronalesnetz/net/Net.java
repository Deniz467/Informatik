package me.deniz.neuronalesnetz.net;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

import me.deniz.neuronalesnetz.layer.Layer;
import me.deniz.neuronalesnetz.squishification.Squishification;

public class Net {

  private final List<Layer> layers;
  private final RandomGenerator random;
  private final int inputSize;

  private Net(List<Layer> layers, RandomGenerator random, int inputSize) {
    this.layers = layers;
    this.random = random;
    this.inputSize = inputSize;
  }

  public static Net create(int inputSize, RandomGenerator random) {
    return new Net(new ArrayList<>(), random, inputSize);
  }

  public Net addLayer(Layer layer) {
    layers.add(layer);
    return this;
  }

  public Net addLayer(int neuronCount, Squishification squishification) {
     int weightAmount = layers.isEmpty() ? inputSize : layers.get(layers.size() - 1).getNeuronCount();
    
    return addLayer(Layer.create(neuronCount, weightAmount, squishification, random));
  }

  public List<Double> calculate(List<Double> input) {
     if (input.size() != inputSize) {
      throw new IllegalArgumentException("Input size (" + input.size() + ") does not match expected input size (" + inputSize + ")");
    }
    
    var currentInput = input;
    for (var layer : layers) {
      currentInput = layer.calculate(currentInput);
    }
    return currentInput;
  }
}
