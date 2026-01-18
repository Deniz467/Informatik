package me.deniz.neuronalesnetz.net;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

import me.deniz.neuronalesnetz.layer.Layer;
import me.deniz.neuronalesnetz.squishification.Squishification;

public class Net {

  private final List<Layer> layers;
  private final RandomGenerator random;

  private Net(List<Layer> layers, RandomGenerator random) {
    this.layers = layers;
    this.random = random;
  }

  public static Net create(RandomGenerator random) {
    return new Net(new ArrayList<>(), random);
  }

  public Net addLayer(Layer layer) {
    layers.add(layer);
    return this;
  }

  public Net addLayer(int neuronCount, Squishification squishification) {
    return addLayer(Layer.create(neuronCount, squishification, random));
  }

  public List<Double> calculate(List<Double> input) {
    var currentInput = input;
    for (var layer : layers) {
      currentInput = layer.calculate(currentInput);
    }
    return currentInput;
  }
}
