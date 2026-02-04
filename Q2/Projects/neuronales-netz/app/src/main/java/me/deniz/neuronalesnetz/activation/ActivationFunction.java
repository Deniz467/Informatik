package me.deniz.neuronalesnetz.activation;

import java.io.Serializable;


/**
 * Represents an activation function used by a neuron.
 *
 * <p>An activation function takes a numeric input value and transforms it
 * into an output value.</p>
 *
 * <p>Each activation function also provides its derivative, which is needed
 * during training (for example in backpropagation).</p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Activation_function#Table_of_activation_functions">
 *      Activation function (Wikipedia)</a>
 */
public interface ActivationFunction extends Serializable {

  /**
   * Applies the activation function to the given value.
   *
   * @param x the input value
   * @return the activated output value
   */
  double activate(double x);

  /**
   * Calculates the derivative of the activation function at the given value.
   *
   * @param x the input value (before activation)
   * @return the derivative value
   */
  double derivative(double x);
}
