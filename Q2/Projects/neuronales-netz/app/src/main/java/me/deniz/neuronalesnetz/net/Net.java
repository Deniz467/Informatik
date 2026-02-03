package me.deniz.neuronalesnetz.net;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import me.deniz.neuronalesnetz.CostFunctions;
import me.deniz.neuronalesnetz.activation.ActivationFunction;
import me.deniz.neuronalesnetz.layer.Layer;
import me.deniz.neuronalesnetz.net.NetParam.BiasParam;
import me.deniz.neuronalesnetz.net.NetParam.WeightParam;
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

  private List<NetParam> getAllParams() {
    var params = new ArrayList<NetParam>();
    for (Layer layer : layers) {
      for (var neuron : layer.getNeurons()) {
        for (int weightIndex = 0; weightIndex < neuron.getMutableWeights().size(); weightIndex++) {
          params.add(new WeightParam(neuron, weightIndex));
        }
        params.add(new BiasParam(neuron));
      }
    }
    return params;
  }

  public void train(List<Double> input, List<Double> target, double learningRate, double epsilon) {
    checkNotNull(input, "input");
    checkNotNull(target, "target");
    checkArgument(input.size() == inputSize, "Input size must match input size of net");
    checkArgument(target.size() == layers.getLast().getNeuronCount(),
        "Target size must match output size of last layer");
    checkArgument(learningRate > 0, "learningRate must be positive");
    checkArgument(epsilon > 0, "epsilon must be positive");
    checkArgument(epsilon < 1, "epsilon must be smaller than 1");

    // collect all trainable parameters (weights and biases)
    var params = getAllParams();

    // for each parameter, compute its gradient and update it based on the learning rate
    for (NetParam param : params) {
      // save the original value to reset later
      var original = param.get();

      // try plus epsilon
      param.set(original + epsilon);
      var outputPlusEpsilon = feedForward(input);
      var costPlusEpsilon = CostFunctions.meanSquaredError(outputPlusEpsilon, target);

      // try minus epsilon
      param.set(original - epsilon);
      var outputMinusEpsilon = feedForward(input);
      var costMinusEpsilon = CostFunctions.meanSquaredError(outputMinusEpsilon, target);

      // compute gradient (how much did the cost change?)
      var gradient = (costPlusEpsilon - costMinusEpsilon) / (2 * epsilon);

      // go in the opposite direction of the gradient
      var updatedWeight = original - learningRate * gradient;

      // update the parameter
      param.set(updatedWeight);
    }
  }

  public static Net create(int inputSize, RandomGenerator random) {
    checkNotNull(random, "random");
    checkArgument(inputSize > 0, "inputSize must be positive");

    return new Net(new ArrayList<>(), random, inputSize);
  }
}
