package me.deniz.neuronalesnetz;

public final class ErrorFunctions {
  private ErrorFunctions() {
    throw new UnsupportedOperationException();
  }

  public static double mse(double predicted, double actual) {
    double error = predicted - actual;
    return Math.pow(error, 2);
  }
}
