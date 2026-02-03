package me.deniz.neuronalesnetz.net;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import me.deniz.neuronalesnetz.CostFunctions;
import me.deniz.neuronalesnetz.activation.ActivationFunction;
import me.deniz.neuronalesnetz.layer.Layer;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class Net {

  private final List<Layer> layers;
  private final RandomGenerator random;
  private final int inputSize;

  private Net(List<Layer> layers, RandomGenerator random, int inputSize) {
    this.layers = layers;
    this.random = random;
    this.inputSize = inputSize;
  }

  public Net addLayer(Layer layer) {
    layers.add(checkNotNull(layer, "layer"));
    return this;
  }

  public Net addLayer(int neuronCount, ActivationFunction activationFunction) {
    int weightAmount = layers.isEmpty() ? inputSize : layers.getLast().getNeuronCount();
    return addLayer(Layer.create(neuronCount, weightAmount, activationFunction, random));
  }

  public List<Double> feedForward(List<Double> input) {
    checkNotNull(input, "input");
    checkArgument(input.size() == inputSize, "Input size must match input size of net");

    var currentInput = input;
    for (Layer layer : layers) {
      currentInput = layer.computeOutputs(currentInput);
    }
    return currentInput;
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
      double costPlus = CostFunctions.meanSquaredError(feedForward(input), target);
      // minus
      params.set(i, original - epsilon);
      setAllParams(params);
      double costMinus = CostFunctions.meanSquaredError(feedForward(input), target);
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

  public static Net create(int inputSize, RandomGenerator random) {
    checkNotNull(random, "random");
    checkArgument(inputSize > 0, "inputSize must be positive");

    return new Net(new ArrayList<>(), random, inputSize);
  }
}
