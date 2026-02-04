package me.deniz.neuronalesnetz;

import it.unimi.dsi.fastutil.ints.IntList;

public final class Settings {

  private Settings() {
    throw new UnsupportedOperationException();
  }


  public static final int IMG_WIDTH = 28;
  public static final int IMG_HEIGHT = 28;

  public static final double LEARNING_RATE = 0.001;
  public static final double EPSILON = 0.001;
  public static final int TRAINING_ROUNDS = 100;
  public static final int STEPS_PER_ROUND = 100000;

  public static final int TESTING_SIZE = 1000;

  public static final IntList LABELS = IntList.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
}
