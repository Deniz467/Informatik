import java.util.List;
import java.util.Scanner;

public class SumParser {

  private static final String PLUS = "+";

  public static void main(String[] args) {
    final Scanner scanner = new Scanner(System.in);
    final String line = scanner.nextLine();

    if (line.startsWith(PLUS) || line.endsWith(PLUS)) {
      throw new IllegalArgumentException("Cannot start or end with a '+'!");
    }

    if (line.contains(" ")) {
      throw new IllegalArgumentException("Cannot contain spaces!");
    }

    if (line.contains(PLUS + PLUS)) {
      throw new IllegalArgumentException("Cannot contain consecutive '+'!");
    }

    System.out.println("Valid input: " + line);

  }

  /* 
  static class Tokenizer {

    private final Scanner scanner;

    Tokenizer(final Scanner scanner) {
      this.scanner = scanner;
    }

    List<Token> tokenize() {

    }
  }


  record Token(Kind kind) {

    enum Kind {
      EXPRESSION, VARIABLE
    }
  }
  */
}
