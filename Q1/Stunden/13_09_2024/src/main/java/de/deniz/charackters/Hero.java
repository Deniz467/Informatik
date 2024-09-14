package de.deniz.charackters;

import de.deniz.tools.Weapon;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a hero character in the game.
 */
public class Hero extends Character<Monster> {

  private final int level;
  private final @Nullable Weapon weapon;

  public Hero(String name, int level, float initialHealth, float attackDamage) {
    this(name, level, initialHealth, attackDamage, null);
  }

  public Hero(String name, int level, float initialHealth, float attackDamage, @Nullable Weapon weapon) {
    super(name, initialHealth, attackDamage);
    this.level = level;
    this.weapon = weapon;
  }

  @Override
  public void takeDamage(float amount) {
    float reducedDamage = Math.max(1f, amount - (level * 2));
    super.takeDamage(reducedDamage);
  }

  @Override
  protected void attack0(Monster target) {
    float totalDamage = getAttackDamage();

    if (weapon != null) {
      totalDamage += weapon.getDamage();
      System.out.println(
          getName() + " greift mit " + weapon.getName() + " an und verursacht " + totalDamage
              + " Schaden!");
    } else {
      System.out.println(
          getName() + " greift ohne Waffe an und verursacht " + totalDamage + " Schaden!");
    }

    target.takeDamage(totalDamage);
  }

  public int getLevel() {
    return level;
  }

  public @Nullable Weapon getWeapon() {
    return weapon;
  }
}
