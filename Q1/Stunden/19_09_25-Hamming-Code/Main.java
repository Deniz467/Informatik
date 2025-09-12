import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
  private static final Pattern NUMERIC_PATTERN = Pattern.compile("^[0-1]*$");

  public static void main(String[] args) {
    final Scanner scanner = new Scanner(System.in);
    final int[] ints = readInput(scanner);
    final int[] withParity = Hamming74Code.calculateParityBits(ints);

    System.out.println(Arrays.toString(ints));
    System.out.println(Arrays.toString(withParity));
  }

  private static int[] readInput(Scanner scanner) {
    System.out.println("Gebe die Bitfolge ein:");
    System.out.print("> ");

    final String input = scanner.nextLine().trim();
    if (!NUMERIC_PATTERN.matcher(input).matches()) {
      throw new IllegalArgumentException("You can only enter 0 and 1!");
    }

    if (input.length() != 4) {
      throw new IllegalArgumentException("You must enter exactly 4 digits!");
    }

    final int[] ints = new int[4];


    for (int i = 0; i < input.length(); i++) {
      ints[i] = Integer.parseInt(input.substring(i, i + 1));
    }

    return ints;
  }
}
