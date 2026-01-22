package me.deniz.neuronalesnetz;

import java.util.List;

public final class CostFunctions {
  private CostFunctions() {
    throw new UnsupportedOperationException();
  }


  // https://medium.com/data-science/step-by-step-the-math-behind-neural-networks-490dc1f3cfd9
  public static double msl(List<Double> output, List<Double> target) {
    if (output.size() != target.size()) {
      throw new IllegalArgumentException("Output size and target size must be the same");
    }

    double sum = 0;
    for (int i = 0; i < output.size(); i++) {
      double diff = output.get(i) - target.get(i);
      sum += diff * diff;
    }

    return sum / output.size();
  }
}
