import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Hamming74Distance {

  private static final Pattern SEVEN_BITS_PATTERN = Pattern.compile("^[01]{7}$");

  public static void distance(Scanner scanner) {
    final int[] code = readInput(scanner);
    final int[][] codes = Hamming74Code.generateAllCodewords();
    final StringBuilder sb = new StringBuilder();

    sb.append("Abstand zu anderen Codewörtern:\n");
    for (int[] other : codes) {
      if (Arrays.equals(other, code)) {
        continue;
      }

      final int distance = Hamming74Code.distance(code, other);
      sb.append(String.format("%s: %d\n", Hamming74Code.toBitString(other), distance));
    }

    System.out.println(sb);
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
