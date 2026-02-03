package me.deniz.neuronalesnetz.neuron;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import me.deniz.neuronalesnetz.activation.ActivationFunction;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class Neuron {

  private final List<Double> weights;
  private double bias;
  private ActivationFunction activationFunction;

  private Neuron(List<Double> weights, ActivationFunction activationFunction, double bias) {
    this.weights = new ArrayList<>(weights);
    this.activationFunction = activationFunction;
    this.bias = bias;
  }

  public double computeOutput(List<Double> input) {
    checkNotNull(input, "input");
    checkArgument(input.size() == weights.size(), "Input size must match weights size");

    double sum = 0;
    for (int i = 0; i < input.size(); i++) {
      sum += input.get(i) * weights.get(i);
    }

    sum += bias;
    return activationFunction.activate(sum);
  }

  public void setActivation(ActivationFunction activationFunction) {
    this.activationFunction = checkNotNull(activationFunction, "activationFunction");
  }

  public List<Double> getMutableWeights() {
    return weights;
  }

  public double getBias() {
    return bias;
  }

  public void setBias(double bias) {
    this.bias = bias;
  }

  public static Neuron create(
      double bias,
      ActivationFunction activationFunction,
      int weightAmount,
      RandomGenerator random
  ) {
    checkArgument(weightAmount > 0, "weightAmount must be positive");
    checkNotNull(random, "random");
    checkNotNull(activationFunction, "activationFunction");

    var randomWeights = random.doubles(-1, 1)
        .limit(weightAmount)
        .boxed()
        .toList();

    return new Neuron(randomWeights, activationFunction, bias);
  }
}
