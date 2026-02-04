package me.deniz.neuronalesnetz;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import org.jspecify.annotations.NullMarked;


/**
 * Utility class that contains cost functions for the neural network.
 *
 * <p>A cost function measures how far the network output is from the
 * expected target output.</p>
 *
 * @see <a href="https://medium.com/machine-learning-for-li/a-walk-through-of-cost-functions-4767dff78f7">Cost Functions</a>
 */
@NullMarked
public final class CostFunctions {

  private CostFunctions() {
    throw new UnsupportedOperationException();
  }


  /**
   * Calculates the Mean Squared Error between the network output
   * and the target values.
   *
   * @param output the output values produced by the neural network
   * @param target the expected target values
   * @return the mean squared error between output and target
   * @throws IllegalArgumentException if output and target have different sizes
   */
  public static double meanSquaredError(DoubleList output, DoubleList target) {
    checkNotNull(output, "output");
    checkNotNull(target, "target");
    checkArgument(output.size() == target.size(), "Output size and target size must be the same");

    double sum = 0;

    // Sum of squared differences
    for (int i = 0; i < output.size(); i++) {
      double diff = output.getDouble(i) - target.getDouble(i);
      sum += diff * diff;
    }

    // Return the average error
    return sum / output.size();
  }
}
