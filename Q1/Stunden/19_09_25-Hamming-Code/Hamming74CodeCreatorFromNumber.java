import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Hamming74CodeCreatorFromNumber {

  private static final Pattern NUMBER_PATTERN = Pattern.compile("\\b([0-9]|1[0-5])\\b");

  public static void create(Scanner scanner) {
    final int input = readInput(scanner);
    final int[] data = Hamming74Code.toDataCode(input);
    final int[] withParity = Hamming74Code.calculateParityBits(data);

    System.out.println("Datenbits: " + Arrays.toString(data));
    System.out.println("Mit Paritäten: " + Hamming74Code.toBitString(withParity));
  }

  private static int readInput(Scanner scanner) {
    while (true) {
      System.out.println("Gebe eine Zahle ein:");
      System.out.print("> ");
      final String input = scanner.nextLine().trim();

      if (!NUMBER_PATTERN.matcher(input).matches()) {
        System.err.println("Du musst eine Zahl zwischen 0 und 15 eingeben!");
        continue;
      }

      return Integer.parseInt(input);
    }
  }



}
