import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Hamming74CodeCreator {
  private static final Pattern FOUR_BITS_PATTERN = Pattern.compile("^[01]{4}$");

  public static void create(Scanner scanner) {
    final int[] data = readInput(scanner);
    final int[] withParity = Hamming74Code.calculateParityBits(data);

    System.out.println("Datenbits: " + Arrays.toString(data));
    System.out.println("Mit Paritäten: " + Hamming74Code.toBitString(withParity));
  }

  private static int[] readInput(Scanner scanner) {
    while (true) {
      System.out.println("Gebe die Bitfolge ein:");
      System.out.print("> ");
      final String input = scanner.nextLine().trim();

      if (!FOUR_BITS_PATTERN.matcher(input).matches()) {
        System.err.println("Du musst genau 4 Bits (0 und 1) eingeben!");
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
