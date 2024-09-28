package me.deniz.vocab;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    final Scanner scanner = new Scanner(System.in);
    final VokabelTrainer trainer = new VokabelTrainer();

    printStartScreen();
    while (scanner.hasNextLine()) {
      switch (scanner.nextLine().trim().toLowerCase()) {
        case "start":
          trainer.prepare(scanner);
          trainer.start(scanner);
          break;
        case "exit":
          trainer.interrupt();
          System.exit(0);
          break;
        default:
          System.err.println("\nUngültige Eingabe!");
          printStartScreen();
      }
    }
  }

  private static void printStartScreen() {
    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    System.out.println("--- Vokabeltrainer ---");
    System.out.println("Befehle:");
    System.out.println("\tstart - Startet den Vokabeltrainer");
    System.out.println("\texit - Beendet das Programm");
    System.out.println("----------------------");
    System.out.print("\nEingabe: ");
  }
}
