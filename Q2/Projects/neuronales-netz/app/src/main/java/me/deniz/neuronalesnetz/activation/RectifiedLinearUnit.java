package me.deniz.neuronalesnetz.activation;

/**
 * Represents the Rectified Linear Unit (ReLU) activation function.
 *
 * <p>This function returns {@code 0} for all negative inputs and
 * returns the input value itself if it is positive.</p>
 *
 * <p>This activation function is often used in hidden layers because
 * it is simple and efficient.</p>
 */
public enum RectifiedLinearUnit implements ActivationFunction {
  INSTANCE;

  @Override
  public double activate(double x) {
    return Math.max(0, x);
  }

  @Override
  public double derivative(double x) {
    return x > 0 ? 1 : 0;
  }
}
