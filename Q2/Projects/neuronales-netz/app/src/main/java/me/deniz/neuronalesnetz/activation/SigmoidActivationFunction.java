package me.deniz.neuronalesnetz.activation;

import java.io.Serial;

/**
 * Sigmoid activation function.
 *
 * <p>The sigmoid function maps input values to a range between {@code 0} and {@code 1}.
 * This makes it useful for output layers in classification tasks.</p>
 */
public enum SigmoidActivationFunction implements ActivationFunction {
  INSTANCE;

  @Serial
  private static final long serialVersionUID = -3256281661776001912L;

  @Override
  public double activate(double x) {
    return 1.0 / (1.0 + Math.exp(-x));
  }

  @Override
  public double derivative(double x) {
    var g = activate(x);
    return g * (1 - g);
  }
}