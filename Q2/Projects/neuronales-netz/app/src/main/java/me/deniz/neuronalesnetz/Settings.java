package me.deniz.neuronalesnetz;

import it.unimi.dsi.fastutil.ints.IntList;

/**
 * Settings for the neural network training and testing.
 */
public final class Settings {

  private Settings() {
    throw new UnsupportedOperationException();
  }

  /**
   * Width of the input images.
   */
  public static final int IMG_WIDTH = 28;

  /**
   * Height of the input images.
   */
  public static final int IMG_HEIGHT = 28;

  /**
   * Learning rate used during training.
   *
   * <p>This value controls how strongly the weights are updated in each
   * training step. Smaller values lead to slower but more stable learning,
   * larger values speed up learning but can cause instability.</p>
   */
  public static final double LEARNING_RATE = 0.001;

  /**
   * Small epsilon value used for numerical gradient approximation.
   */
  public static final double EPSILON = 0.001;

  /**
   * Number of full training rounds.
   *
   * <p>Each training round consists of {@link #STEPS_PER_ROUND} individual
   * training steps.</p>
   */
  public static final int TRAINING_ROUNDS = 100;

  /**
   * Number of training steps per round.
   *
   * <p>In each step, a random training sample is selected and the network
   * is updated once.</p>
   */
  public static final int STEPS_PER_ROUND = 10_000;

  /**
   * Number of samples used during testing/evaluation.
   *
   * <p>The testing process randomly selects this many samples and reports
   * the classification accuracy.</p>
   */
  public static final int TESTING_SIZE = 10000;

  /**
   * List of all possible class labels.
   */
  public static final IntList LABELS = IntList.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
}
