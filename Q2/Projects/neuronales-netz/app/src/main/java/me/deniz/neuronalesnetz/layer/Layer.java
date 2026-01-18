package me.deniz.neuronalesnetz.layer;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

import me.deniz.neuronalesnetz.neuron.Neuron;
import me.deniz.neuronalesnetz.squishification.Squishification;

public class Layer {

  private final List<Neuron> neurons;

  private Layer(List<Neuron> neurons) {
    this.neurons = neurons;
  }

  public static Layer create(int neuronCount, Squishification squishification,
      RandomGenerator random) {
    var neurons = new ArrayList<Neuron>(neuronCount);
    for (int i = 0; i < neuronCount; i++) {
      neurons.add(Neuron.create(random.nextDouble(-1, 1), squishification, random));
    }

    return new Layer(neurons);
  }

  public List<Double> calculate(List<Double> input) {
    var outputs = new ArrayList<Double>(neurons.size());
    for (var neuron : neurons) {
      outputs.add(neuron.calculate(input));
    }
    return outputs;
  }
}
