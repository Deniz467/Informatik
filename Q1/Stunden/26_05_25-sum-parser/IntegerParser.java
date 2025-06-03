import java.util.Scanner;

public class IntegerParser {
  public static void main(String[] args) {
    final Scanner scanner = new Scanner(System.in);
    final String line = scanner.nextLine();
    final String sign = line.startsWith("-") ? "-" : "+";
    final String number = line.replaceFirst("[+-]", "");

    if (!number.matches("\\d+")) {
      throw new IllegalArgumentException("Invalid input: " + line);
    }

    System.out.println("Valid input: " + line);
  }
}
