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

/**
 * Represents a single neuron in the neural network.
 */
@NullMarked
public final class Neuron implements Serializable {

  @Serial
  private static final long serialVersionUID = 252308909756653921L;

  /**
   * The weights of this neuron. One weight exists for each input value.
   */
  private final DoubleList weights;

  /**
   * The bias value of the neuron.
   */
  private double bias;

  /**
   * The activation function used by this neuron.
   */
  private final ActivationFunction activationFunction;

  /**
   * The input values from the last computation. This is only used during training and is not
   * serialized.
   */
  @Nullable
  private transient DoubleList lastInput;

  /**
   * The weighted sum of the input values from the last computation.
   * This is only used during training and is not serialized.
   */
  private transient double lastWeightedInputSum;

  /**
   * The output value from the last computation. This is only used during training and is not
   * serialized.
   */
  private transient double lastOutput;

  private Neuron(DoubleList weights, ActivationFunction activationFunction, double bias) {
    this.weights = new DoubleArrayList(weights);
    this.activationFunction = activationFunction;
    this.bias = bias;
  }

  /**
   * Computes the output of this neuron for the given input values.
   *
   * <p>The output is calculated as follows:</p>
   * <ol>
   *   <li>Multiply each input value with its corresponding weight</li>
   *   <li>Add all results together and add the bias</li>
   *   <li>Apply the activation function</li>
   * </ol>
   *
   * @param input the input values
   * @return the output value of the neuron
   * @throws IllegalArgumentException if the input size does not match the number of weights
   */
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

  /**
   * Creates a new neuron with randomly initialized weights.
   *
   * <p>The weights are initialized with small random values based on the
   * number of inputs.</p>
   *
   * @param bias the initial bias value
   * @param activationFunction the activation function
   * @param weightAmount the number of weights (number of inputs)
   * @param random the random generator used to create the weights
   * @return a new neuron instance
   */
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
