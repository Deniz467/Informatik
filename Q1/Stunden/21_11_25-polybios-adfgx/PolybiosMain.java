import java.util.Arrays;
import java.util.Scanner;

public class PolybiosMain {

  public static void main(String[] args) {
    final Scanner scanner = new Scanner(System.in);

    while (true) {
      menu(scanner);
    }
  }

  private static void menu(Scanner scanner) {
    System.out.println("---------- Polybios Verschlüsselung ----------");
    System.out.println("1. Verschlüsseln");
    System.out.println("2. Entschlüsseln");
    System.out.println("3. Programm verlassen");

    final int input = scanner.nextInt();
    scanner.nextLine();

    switch (input) {
      case 1:
        encrypt(scanner);
        break;
      case 2:
        decrypt(scanner);
        break;
      case 3:
        System.exit(0);
        break;
      default:
        System.out.println("Bitte gebe eine Zahl zwischen 1 und 3 an!");
    }
  }

  private static void encrypt(Scanner scanner) {
    System.out.print("Schlüssel: ");
    final String key = scanner.nextLine();
    System.out.print("Text: ");
    final String text = scanner.next();
    
    final int[] encrypted = PolybiosEntryption.encrypt(key, text);
    System.out.println("Verschlüsselt: " + Arrays.toString(encrypted));
  }

  private static void decrypt(Scanner scanner) {

  }
}
