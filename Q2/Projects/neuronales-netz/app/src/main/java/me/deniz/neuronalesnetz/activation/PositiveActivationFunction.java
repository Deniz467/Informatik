package me.deniz.neuronalesnetz.activation;

import java.io.Serial;

public enum PositiveActivationFunction implements ActivationFunction {
  INSTANCE;

  @Serial
  private static final long serialVersionUID = -2771254372312251589L;

  @Override
  public double activate(double x) {
    return Math.max(0, x);
  }

  @Override
  public double derivative(double x) {
    return x > 0 ? 1 : 0;
  }
}
