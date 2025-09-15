import java.util.Scanner;
import java.util.regex.Pattern;

public class Hamming74Distance {

  private static final Pattern SEVEN_BITS_PATTERN = Pattern.compile("^[01]{7}$");

  public static void distance(Scanner scanner) {
    final int[] code = readInput(scanner);

    
  }

  private static int[] readInput(Scanner scanner) {
    while (true) {
      System.out.println("Gebe das Hamming-Codewort ein:");
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
