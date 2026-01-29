package me.deniz.neuronalesnetz.net;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

import me.deniz.neuronalesnetz.CostFunctions;
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
    int weightAmount =
        layers.isEmpty() ? inputSize : layers.get(layers.size() - 1).getNeuronCount();

    return addLayer(Layer.create(neuronCount, weightAmount, squishification, random));
  }

  public List<Double> calculate(List<Double> input) {
    if (input.size() != inputSize) {
      throw new IllegalArgumentException("Input size (" + input.size()
          + ") does not match expected input size (" + inputSize + ")");
    }

    var currentInput = input;
    for (var layer : layers) {
      currentInput = layer.calculate(currentInput);
    }
    return currentInput;
  }

  public List<Layer> getLayers() {
    return layers;
  }

  private List<Double> getAllParams() {
    List<Double> params = new ArrayList<>();
    for (Layer layer : layers) {
      for (var neuron : layer.getNeurons()) {
        params.addAll(neuron.getWeights());
        params.add(neuron.getBias());
      }
    }
    return params;
  }

  private void setAllParams(List<Double> params) {
    int idx = 0;
    for (Layer layer : layers) {
      for (var neuron : layer.getNeurons()) {
        for (int i = 0; i < neuron.getWeights().size(); i++) {
          neuron.getWeights().set(i, params.get(idx++));
        }
        neuron.setBias(params.get(idx++));
      }
    }
  }

  public void train(List<Double> input, List<Double> target, double learningRate, double epsilon) {
    List<Double> params = getAllParams();
    List<Double> gradients = new ArrayList<>(params.size());
    for (int i = 0; i < params.size(); i++) {
      double original = params.get(i);
      // plus
      params.set(i, original + epsilon);
      setAllParams(params);
      double costPlus = CostFunctions.msl(calculate(input), target);
      // minus
      params.set(i, original - epsilon);
      setAllParams(params);
      double costMinus = CostFunctions.msl(calculate(input), target);
      double grad = (costPlus - costMinus) / (2 * epsilon);
      gradients.add(grad);
      // reset
      params.set(i, original);
    }
    setAllParams(params); // reset to original
    // update
    for (int i = 0; i < params.size(); i++) {
      params.set(i, params.get(i) - learningRate * gradients.get(i));
    }
    setAllParams(params);
  }
}
