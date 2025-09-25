import java.util.Scanner;

public class Main {


  public static void main(String[] args) {
    final Scanner scanner = new Scanner(System.in);
    boolean running = true;

    while (running) {
      printMenu();
      final int choice = readBoundedInt(scanner, 0, 4);

      switch (choice) {
        case 1 -> Hamming74CodeCreator.create(scanner);
        case 2 -> Hamming74CodeValidator.validate(scanner);
        case 3 -> Hamming74Distance.distance(scanner);
        case 4 -> Hamming74CodeCreatorFromNumber.create(scanner);
        case 0 -> {
          System.out.println("Exiting...");
          running = false;
        }

        default -> System.out.println("Ungültige Auswahl. Bitte versuche es erneut.");
      }
    }

    scanner.close();
  }

  private static void printMenu() {
    System.out.println();
    System.out.println("Wähle eine Option:");
    System.out.println("1. Hamming-Code (7,4) erstellen");
    System.out.println("2. Hamming-Code (7,4) prüfen");
    System.out.println("3. Hamming-Code (7,4) Abstände berechnen");
    System.out.println("4. Hamming-Code (7,4) aus Zahl erstellen");
    System.out.println("0. Beenden");
    System.out.print("> ");
  }

  private static Integer readBoundedInt(Scanner scanner, int min, int max) {
    while (true) {
      final String line = scanner.nextLine().trim();
      try {
        final int value = Integer.parseInt(line);
        if (value < min || value > max) {
          System.out.print("Bitte gebe eine Zahl zwischen " + min + " und " + max + " ein: ");
          continue;
        }
        return value;
      } catch (NumberFormatException e) {
        System.out.print("Keine gültige Zahl. Bitte versuche es erneut: ");
      }
    }
  }
}
