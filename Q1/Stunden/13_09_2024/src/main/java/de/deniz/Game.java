package de.deniz;

import de.deniz.charackters.Hero;
import de.deniz.charackters.Monster;
import de.deniz.tools.Weapon;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.jetbrains.annotations.Nullable;

public class Game {

  private final Scanner scanner;
  private final List<Hero> heroes;
  private final List<Monster> monsters;

  public Game(Scanner scanner) {
    this.scanner = scanner;

    this.heroes = new ArrayList<>();
    this.monsters = new ArrayList<>();

    // add test data
    heroes.add(new Hero("Hero1", 1, 100, 10));
    heroes.add(new Hero("Hero2", 2, 100, 5));

    monsters.add(new Monster("Monster1", 100, 50));
    monsters.add(new Monster("Monster2", 100, 10));
  }

  public void start() {
    System.out.println("Willkommen zum Spiel!");

    while (true) {
      mainMenu();
    }
  }

  public void mainMenu() {
    System.out.println("\nWillkommen zum Spiel!");
    System.out.println("1. Helden erstellen");
    System.out.println("2. Monster erstellen");
    System.out.println("3. Kampf starten");
    System.out.println("4. Beenden");

    System.out.print("Wähle eine Option: ");
    final int choice = scanner.nextInt();
    scanner.nextLine();

    switch (choice) {
      case 1:
        createHeroes();
        break;
      case 2:
        createMonsters();
        break;
      case 3:
        startBattle();
        break;
      case 4:
        System.out.println("Spiel wird beendet!");
        System.exit(0);
      default:
        System.err.println("Ungültige Option! Gib eine Zahl zwischen 1 und 4 ein.");

    }
  }

  private void createHeroes() {
    System.out.print("Wie viele Helden möchtest du erstellen? ");
    final int count = scanner.nextInt();
    scanner.nextLine();

    for (int i = 0; i < count; i++) {
      System.out.println("\nHeld #" + (i + 1));
      System.out.print("Name: ");
      final String name = scanner.nextLine();

      System.out.print("Level: ");
      final int level = scanner.nextInt();
      scanner.nextLine();

      System.out.print("Lebenspunkte: ");
      final float health = scanner.nextFloat();
      scanner.nextLine();

      System.out.print("Angriffsschaden: ");
      final float attackDamage = scanner.nextFloat();
      scanner.nextLine();

      final @Nullable Weapon weapon;
      System.out.print("Möchtest du dem Helden eine Waffe geben? (j/n) ");
      final String weaponChoice = scanner.next();
      scanner.nextLine();

      if (weaponChoice.equalsIgnoreCase("j")) {
        System.out.print("Waffenname: ");
        final String weaponName = scanner.nextLine();

        System.out.print("Waffenschaden: ");
        final float weaponDamage = scanner.nextFloat();
        scanner.nextLine();

        weapon = new Weapon(weaponName, weaponDamage);
      } else {
        weapon = null;
      }

      heroes.add(new Hero(name, level, health, attackDamage, weapon));
      System.out.println("Held " + name + " wurde erstellt!");
    }
  }

  private void createMonsters() {
    System.out.print("Wie viele Monster möchtest du erstellen? ");
    final int count = scanner.nextInt();
    scanner.nextLine();

    for (int i = 0; i < count; i++) {
      System.out.println("\nMonster #" + (i + 1));
      System.out.print("Name: ");
      final String name = scanner.nextLine();

      System.out.print("Lebenspunkte: ");
      final float health = scanner.nextFloat();
      scanner.nextLine();

      System.out.print("Angriffsschaden: ");
      final float attackDamage = scanner.nextFloat();
      scanner.nextLine();

      monsters.add(new Monster(name, health, attackDamage));
      System.out.println("Monster " + name + " wurde erstellt!");
    }
  }

  private void startBattle() {
    if (heroes.isEmpty() || monsters.isEmpty()) {
      System.err.println("Es müssen Helden und Monster erstellt werden!\n");
      return;
    }

    System.out.println("\nKampf startet!");

    int heroIndex = 0;
    int monsterIndex = 0;

    while (heroIndex < heroes.size() && monsterIndex < monsters.size()) {
      Hero currentHero = heroes.get(heroIndex);
      Monster currentMonster = monsters.get(monsterIndex);

      System.out.println("\n" + currentHero.getName() + " vs. " + currentMonster.getName());

      while (!currentHero.isDead() && !currentMonster.isDead()) {
        System.out.println("\nWas soll " + currentHero.getName() + " tun?");
        System.out.println("1. Angreifen");
        System.out.println("2. Nächster Held");
        System.out.println("3. Flüchten");

        final int choice = scanner.nextInt();

        switch (choice) {
          case 1:
            currentHero.attack(currentMonster);

            if (!currentMonster.isDead()) {
              currentMonster.attack(currentHero);
            } else {
              monsterIndex++;
              if (monsterIndex < monsters.size()) {
                currentMonster = monsters.get(monsterIndex);
                System.out.println("Ein neues Monster erscheint: " + currentMonster.getName());
              }
            }
            break;
          case 2:
            heroIndex++;

            if (heroIndex < heroes.size()) {
              currentHero = heroes.get(heroIndex);
              System.out.println("Ein neuer Held erscheint: " + currentHero.getName());
            } else {
              System.out.println("Es gibt keine weiteren Helden!");
            }

            break;
          case 3:
            System.out.println("Du bist geflohen!");

            heroIndex++;
            if (heroIndex < heroes.size()) {
              currentHero = heroes.get(heroIndex);
              System.out.println("Der nächste Held ist: " + currentHero.getName());
            } else {
              System.out.println("Alle Helden sind geflohen!");
              break;
            }

            break;
          default:
            System.err.println("Ungültige Option! Gib eine Zahl zwischen 1 und 3 ein.");
        }
      }

      if (currentHero.isDead()) {
        System.out.println(currentHero.getName() + " wurde besiegt!");
        heroIndex++;
        if (heroIndex < heroes.size()) {
          currentHero = heroes.get(heroIndex);
          System.out.println("Der nächste Held ist: " + currentHero.getName());
        } else {
          System.out.println("Alle Helden wurden besiegt!");
          break;
        }
      }
    }

    if (heroIndex >= heroes.size()) {
      System.out.println("\nDie Helden haben gewonnen!");
    } else if (monsterIndex >= monsters.size()) {
      System.out.println("\nDie Monster haben gewonnen!");
    }
  }
}
