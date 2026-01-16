package me.deniz.neuronalesnetz.squishification;

public class PositiveSquishification implements Squishification {

  public static final PositiveSquishification INSTANCE = new PositiveSquishification();

  private PositiveSquishification() {
  }

  @Override
  public double squish(double x) {
    return x < 0 ? 0 : x;
  }
}
