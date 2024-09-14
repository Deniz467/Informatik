package de.deniz.tools;

/**
 * Represents a weapon that can be used by heroes.
 */
public class Weapon {

  private final String name;
  private final float damage;

  public Weapon(String name, float damage) {
    this.name = name;
    this.damage = damage;
  }

  public float getDamage() {
    return damage;
  }

  public String getName() {
    return name;
  }
}
