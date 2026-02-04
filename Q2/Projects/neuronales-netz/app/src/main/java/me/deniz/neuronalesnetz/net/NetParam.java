package me.deniz.neuronalesnetz.net;

import me.deniz.neuronalesnetz.neuron.Neuron;


/**
 * Represents a trainable parameter of the neural network.
 *
 * <p>A {@code NetParam} is an abstraction for values that can be changed
 * during training, such as weights and biases.</p>
 */
public sealed interface NetParam {

  /**
   * Returns the current value of this parameter.
   *
   * @return the parameter value
   */
  double get();

  /**
   * Sets a new value for this parameter.
   *
   * @param value the new parameter value
   */
  void set(double value);

  /**
   * Represents a single weight of a neuron.
   */
  record WeightParam(Neuron neuron, int index) implements NetParam {

    @Override
    public double get() {
      return neuron.getMutableWeights().getDouble(index);
    }

    @Override
    public void set(double value) {
      neuron.getMutableWeights().set(index, value);
    }
  }

  /**
   * Represents the bias parameter of a neuron.
   */
  record BiasParam(Neuron neuron) implements NetParam {

    @Override
    public double get() {
      return neuron.getBias();
    }

    @Override
    public void set(double value) {
      neuron.setBias(value);
    }
  }
}
