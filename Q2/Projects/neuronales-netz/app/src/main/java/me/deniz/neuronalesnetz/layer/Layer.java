package me.deniz.neuronalesnetz.layer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.random.RandomGenerator;
import me.deniz.neuronalesnetz.activation.ActivationFunction;
import me.deniz.neuronalesnetz.neuron.Neuron;
import org.jetbrains.annotations.UnmodifiableView;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class Layer {

  private final List<Neuron> neurons;

  private Layer(List<Neuron> neurons) {
    this.neurons = neurons;
  }

  public List<Double> computeOutputs(List<Double> input) {
    var outputs = new ArrayList<Double>(getNeuronCount());
    for (Neuron neuron : neurons) {
      outputs.add(neuron.computeOutput(input));
    }
    return outputs;
  }

  public int getNeuronCount() {
    return neurons.size();
  }

  @UnmodifiableView
  public List<Neuron> getNeurons() {
    return Collections.unmodifiableList(neurons);
  }

  public static Layer create(
      int neuronCount,
      int weightAmount,
      ActivationFunction activationFunction,
      RandomGenerator random
  ) {
    var neurons = new ArrayList<Neuron>(neuronCount);
    for (int i = 0; i < neuronCount; i++) {
      double bias = random.nextDouble(-1, 1);
      var neuron = Neuron.create(bias, activationFunction, weightAmount, random);
      neurons.add(neuron);
    }

    return new Layer(neurons);
  }
}
