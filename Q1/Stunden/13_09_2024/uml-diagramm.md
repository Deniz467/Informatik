classDiagram
direction BT
class AbstractBase {
  + AbstractBase(int, float) 
  - float damageMultiplier
  - float health
  - float damage
  - int level
  + takeDamage(float) void
  + getDamageMultiplier() float
  + heal(float) void
  + isDead() boolean
  + getDamage() float
  + getHealth() float
  + getLevel() int
}
class Hero {
  + Hero(String, int, float) 
  - String name
  + attack(Monster) void
  + getName() String
}
class Monster {
  + Monster(int, float) 
  + attack(Hero) void
}

Hero  -->  AbstractBase 
Monster  -->  AbstractBase 
