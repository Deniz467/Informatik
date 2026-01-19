package me.deniz.neuronalesnetz.squishification;

public class SigmoidSquishification implements Squishification {

  public static final SigmoidSquishification INSTANCE = new SigmoidSquishification();
  private SigmoidSquishification() {}

  @Override
  public double squish(double x) {
    return (1.0) / (1.0 + Math.exp(-x));
  }
}