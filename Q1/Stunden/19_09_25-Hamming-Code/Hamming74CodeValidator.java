import java.util.Scanner;
import java.util.regex.Pattern;

public class Hamming74CodeValidator {

  private static final Pattern SEVEN_BITS_PATTERN = Pattern.compile("^[01]{7}$");

  public static void validate(Scanner scanner) {
    final int[] code = readInput(scanner);

    if (Hamming74Code.isValid(code)) {
      System.out.println("Das Hamming-Codewort ist gültig.");
    } else {
      // try all other codes with one bit flipped
      for (int i = 0; i < code.length; i++) {
        final int[] candidate = code.clone();
        candidate[i] = 1 - candidate[i]; // flip bit

        if (Hamming74Code.isValid(candidate)) {
          System.out.println(
              "Das Hamming-Codewort ist ungültig. Es konnte jedoch ein Fehler an Position " + (i
                  + 1) + " korrigiert werden.");
          System.out.println(
              "Das korrigierte Hamming-Codewort lautet: " + java.util.Arrays.toString(candidate));
          return;
        }
      }

      System.err.println(
          "Das Hamming-Codewort ist ungültig und es konnte kein einzelner Fehler korrigiert werden.");
    }
  }

  private static int[] readInput(Scanner scanner) {
    while (true) {
      System.out.println("Gebe das zu überprüfende Hamming-Codewort ein:");
      System.out.print("> ");
      final String input = scanner.nextLine().trim();

      if (!SEVEN_BITS_PATTERN.matcher(input).matches()) {
        System.err.println("Du musst genau 7 Bits (0 und 1) eingeben!");
        continue;
      }

      return toIntArray(input);
    }
  }

  private static int[] toIntArray(String bitString) {
    final int[] array = new int[bitString.length()];

    for (int i = 0; i < bitString.length(); i++) {
      array[i] = bitString.charAt(i) - '0';
    }
    return array;
  }
}
