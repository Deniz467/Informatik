package me.deniz.neuronalesnetz.neuron;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.io.Serial;
import java.io.Serializable;
import java.util.random.RandomGenerator;
import me.deniz.neuronalesnetz.activation.ActivationFunction;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public final class Neuron implements Serializable {

  @Serial
  private static final long serialVersionUID = 252308909756653921L;

  private final DoubleList weights;
  private double bias;
  private final ActivationFunction activationFunction;

  @Nullable
  private transient DoubleList lastInput;
  private transient double lastWeightedInputSum;
  private transient double lastOutput;

  private Neuron(DoubleList weights, ActivationFunction activationFunction, double bias) {
    this.weights = new DoubleArrayList(weights);
    this.activationFunction = activationFunction;
    this.bias = bias;
  }

  public double computeOutput(DoubleList input) {
    checkNotNull(input, "input");
    checkArgument(input.size() == weights.size(), "Input size must match weights size");

    this.lastInput = input;

    double sum = 0;
    for (int i = 0; i < input.size(); i++) {
      sum += input.getDouble(i) * weights.getDouble(i);
    }

    sum += bias;

    this.lastWeightedInputSum = sum;
    this.lastOutput = activationFunction.activate(sum);

    return lastOutput;
  }

  public ActivationFunction getActivationFunction() {
    return activationFunction;
  }

  public DoubleList getMutableWeights() {
    return weights;
  }

  public double getBias() {
    return bias;
  }

  public void setBias(double bias) {
    this.bias = bias;
  }

  @Nullable
  public DoubleList getLastInput() {
    return lastInput;
  }

  public double getLastWeightedInputSum() {
    return lastWeightedInputSum;
  }

  public double getLastOutput() {
    return lastOutput;
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

    double scale = 1.0 / Math.sqrt(weightAmount);
    var randomWeights = random.doubles(-scale, scale)
        .limit(weightAmount)
        .collect(DoubleArrayList::new, DoubleArrayList::add, DoubleList::addAll);

    return new Neuron(randomWeights, activationFunction, bias);
  }
}
