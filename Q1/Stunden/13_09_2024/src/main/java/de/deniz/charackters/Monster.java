package de.deniz.charackters;

/**
 * Represents a monster character in the game.
 */
public class Monster extends Character<Hero> {

  public Monster(String name, float initialHealth, float attackDamage) {
    super(name, initialHealth, attackDamage);
  }

  @Override
  protected void attack0(Hero target) {
    System.out.println(
        getName() + " greift " + target.getName() + " an und verursacht " + getAttackDamage()
            + " Schaden!");
    target.takeDamage(getAttackDamage());
  }
}
