package me.deniz.neuronalesnetz.activation;

public class SigmoidActivationFunction implements ActivationFunction {

  public static final SigmoidActivationFunction INSTANCE = new SigmoidActivationFunction();
  private SigmoidActivationFunction() {}

  @Override
  public double activate(double x) {
    return (1.0) / (1.0 + Math.exp(-x));
  }
}