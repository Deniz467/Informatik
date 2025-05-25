import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LaLeLuScanner {

  private static final char END = '#';
  private static final String LA = "la";
  private static final String LE = "le";
  private static final String LU = "lu";


  public static void main(String[] args) {
    final Scanner scanner = new Scanner(System.in);
    final Tokenizer tokenizer = new Tokenizer(scanner);

    System.out.println("Enter tokens (la, le, lu) followed by # to end:");

    final List<Token> tokens = tokenizer.tokenize();
    for (final Token token : tokens) {
      System.out.println(token.kind() + ": " + token.value());
    }
  }

  static class Tokenizer {

    private final Scanner scanner;

    Tokenizer(Scanner scanner) {
      this.scanner = scanner;
    }

    List<Token> tokenize() {
      final String line = scanner.nextLine();
      final List<Token> tokens = new ArrayList<>();

      if (!line.endsWith(String.valueOf(END))) {
        throw new IllegalArgumentException("Input must end with '#'.");
      }

      int index = 0;
      boolean foundEnd = false;
      while (index <= line.length() - 2) {
        final String tokenValue = line.substring(index, index + 2);

        if (tokenValue.equals(LA) || tokenValue.equals(LE)) {
          if (foundEnd) {
            throw new IllegalArgumentException("Unexpected token after end: " + tokenValue);
          }
          tokens.add(new Token(Token.Kind.IRRELEVANT, tokenValue));
        } else if (tokenValue.equals(LU)) {
          if (foundEnd) {
            throw new IllegalArgumentException(
                "Multiple 'lu' tokens after end are not allowed: " + tokenValue);
          }
          tokens.add(new Token(Token.Kind.END, tokenValue));
          foundEnd = true;
        } else if (tokenValue.equals(END + String.valueOf(END))) {
          break;
        } else if (tokenValue.charAt(0) == END) {
          break;
        } else {
          throw new IllegalArgumentException("Unexpected token: " + tokenValue);
        }
        index += 2;
      }

      if (!foundEnd) {
        throw new IllegalArgumentException("Input must end with 'lu' token.");
      }

      if (index != line.length() - 1) {
        throw new IllegalArgumentException(
            "Input contains extra characters after 'lu': " + line.substring(index));
      }

      return tokens;
    }
  }

  record Token(Kind kind, String value) {

    enum Kind {
      END, IRRELEVANT,
    }
  }
}
