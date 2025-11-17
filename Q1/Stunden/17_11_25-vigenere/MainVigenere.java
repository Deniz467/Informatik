import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainVigenere {
  public static void main(String[] args) {
    final Scanner scanner = new Scanner(System.in);

    outerWhile: while (true) {
      System.out.println("Was möchtest du machen?");
      System.out.println("1. Einen Text verschlüsseln");
      System.out.println("2. Einen Text entschlüsseln");
      System.out.println("3. Programm verlassen");
      try {
        final int action = scanner.nextInt();
        switch (action) {
          case 1:
            encrypt(scanner);
            break;
          case 2:
            decrypt(scanner);
            break;
          case 3:
            break outerWhile;
          default:
            System.out.println("Bitte gebe eine Zahl zwischen 1 und 3 ein!");
        }

      } catch (InputMismatchException e) {
        System.out.println("Bitte gebe eine gültige Zahl ein!");
      }
    }

    scanner.close();
  }

  private static void encrypt(Scanner scanner) {
    System.out.println("Zu verschlüsselnden Text eingeben: ");
    final String toEncrypt = scanner.next(Pattern.compile("[^a-zA-Z]+/g"));
    System.out.println("Schlüsselwort: ");
    final String codeword = scanner.nextLine();
    final String encrypted = VigenereEncryption.encrypt(toEncrypt, codeword);

    System.out.println("Verschlüsselt: " + encrypted);
  }

  private static void decrypt(Scanner scanner) {
    System.out.println("Zu entschlüsselnden Text eingeben: ");
    final String toDecrypt = scanner.nextLine();
    System.out.println("Schlüsselwort: ");
    final String codeword = scanner.nextLine();
    final String decrypted = VigenereEncryption.decrypt(toDecrypt, codeword);

    System.out.println("Entschlüsselt: " + decrypted);
  }
}
