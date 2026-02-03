package me.deniz.neuronalesnetz.activation;

public class PositiveActivationFunction implements ActivationFunction {

  public static final PositiveActivationFunction INSTANCE = new PositiveActivationFunction();

  private PositiveActivationFunction() {
  }

  @Override
  public double activate(double x) {
    return x < 0 ? 0 : x;
  }
}
