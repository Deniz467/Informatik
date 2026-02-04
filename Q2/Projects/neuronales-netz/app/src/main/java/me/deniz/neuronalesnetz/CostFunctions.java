package me.deniz.neuronalesnetz;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class CostFunctions {

  private CostFunctions() {
    throw new UnsupportedOperationException();
  }


  // https://medium.com/data-science/step-by-step-the-math-behind-neural-networks-490dc1f3cfd9
  public static double meanSquaredError(DoubleList output, DoubleList target) {
    checkNotNull(output, "output");
    checkNotNull(target, "target");
    checkArgument(output.size() == target.size(), "Output size and target size must be the same");

    double sum = 0;
    for (int i = 0; i < output.size(); i++) {
      double diff = output.getDouble(i) - target.getDouble(i);
      sum += diff * diff;
    }

    return sum / output.size();
  }
}
