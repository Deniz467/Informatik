package me.deniz.neuronalesnetz;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class CostFunctions {

  private CostFunctions() {
    throw new UnsupportedOperationException();
  }


  // https://medium.com/data-science/step-by-step-the-math-behind-neural-networks-490dc1f3cfd9
  public static double meanSquaredError(List<Double> output, List<Double> target) {
    checkNotNull(output, "output");
    checkNotNull(target, "target");
    checkArgument(output.size() == target.size(), "Output size and target size must be the same");

    double sum = 0;
    for (int i = 0; i < output.size(); i++) {
      double diff = output.get(i) - target.get(i);
      sum += diff * diff;
    }

    return sum / output.size();
  }
}
