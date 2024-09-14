package de.deniz.charackters;

/**
 * Abstract base class representing a character in the game.
 */
public abstract class Character<Attackable extends Character<?>> {

  private final String name;
  private float health;
  private final float attackDamage;

  public Character(String name, float initialHealth, float attackDamage) {
    this.name = name;
    this.health = initialHealth;
    this.attackDamage = attackDamage;
  }

  public final void attack(Attackable target) {
    if (canAttack(target)) {
      attack0(target);
    }
  }

  protected abstract void attack0(Attackable target);

  protected boolean canAttack(Attackable target) {
    return !isDead() && !target.isDead();
  }

  public void heal(float amount) {
    health += amount;
  }

  public void takeDamage(float amount) {
    health -= amount;

    if (isDead()) {
      System.out.println(getName() + " wurde besiegt!");
    } else {
      System.out.println(getName() + " hat noch " + getHealth() + " Lebenspunkte.");
    }
  }

  public boolean isDead() {
    return health <= 0;
  }

  public float getAttackDamage() {
    return attackDamage;
  }

  public float getHealth() {
    return health;
  }

  public String getName() {
    return name;
  }
}
