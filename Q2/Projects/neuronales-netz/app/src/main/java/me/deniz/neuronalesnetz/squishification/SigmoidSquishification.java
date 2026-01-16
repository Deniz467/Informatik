package me.deniz.neuronalesnetz.squishification;

public class SigmoidSquishification implements Squishification {

  public static final SigmoidSquishification INSTANCE = new SigmoidSquishification();
  private SigmoidSquishification() {}

  @Override
  public double squish(double x) {
    return (double) (1) / (1 + Math.exp(-x));
  }
}
