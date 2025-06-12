import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.lang.IllegalArgumentException;

public class AutoParser {

  public static void main(String[] args) {
    final Scanner scanner = new Scanner(System.in);
    final Tokenizer tokenizer = new Tokenizer(scanner.nextLine());
    tokenizer.scan();
  }
  
  public static class Tokenizer {

    private final String input;

    public Tokenizer(String input) {
      this.input = input;
    }

    void scan() {
      for (int i = 0; i < input.length(); i += 2) {
        if (i + 2 > input.length()) {
          throw new IllegalArgumentException(
              "Input length is not a multiple of 2: " + input.length());
        }

        final String currentTerm = input.substring(i, i + 2);
        if (!Kind.shortTerms.contains(currentTerm)) {
          throw new IllegalArgumentException("Input contains unknown short term: " + currentTerm);
        }
      }
    }

    void parse() {
      final List<String> splitted = splitInput();
      final List<Kind> parsed = splitted.stream().map(in -> Kind.fromShortTerm(in)).toList()
      final List<Token> results = new ArrayList<>(splitted.size());

      checkA(results, parsed.iterator());
    }

    private void checkA(List<Token> results, Iterator<Kind> inputs) {
      if (!inputs.hasNext()) {
        throw new IllegalStateException("Expected more input after BLOCK");
      }

      final Kind current = inputs.next();
      switch (current) {
        case BLOCK -> {
          results.add(new Token(current));
          checkA(results, inputs);
        }

        case BLOCK_WITH_WHEEL -> {
          results.add(new Token(current));
          checkB(results, inputs);
        }

        case REAR_WINDOW -> {
          results.add(new Token(current));
          checkC(results, inputs);
        }

        default -> throw new IllegalStateException("Unexpected kind after BLOCK: " + current);
      }
    }
    
    private void checkB(List<Token> results, Iterator<Kind> inputs) {
      if (!inputs.hasNext()) {
        throw new IllegalStateException("Expected more input after BLOCK_WITH_WHEEL");
      }

      final Kind current = inputs.next();
      switch (current) {
        case BLOCK_WITH_WHEEL, BLOCK -> {
          results.add(new Token(current));
          checkB(results, inputs);
        }

        case REAR_WINDOW, WINDOW, DOOR -> {
          results.add(new Token(current));
          checkC(results, inputs);
        }

        case FRONT_WINDOW -> {
          results.add(new Token(current));
          checkC(results, inputs);
        }

        default -> throw new IllegalStateException("Unexpected kind after BLOCK_WITH_WHEEL: " + current);
      }
    }

    private void checkC(List<Token> results, Iterator<Kind> inputs) {
      if (!inputs.hasNext()) {
        throw new IllegalStateException("Expected more input after REAR_WINDOW or FRONT_WINDOW");
      }

      final Kind current = inputs.next();
      switch (current) {
        case WINDOW, DOOR -> {
          results.add(new Token(current));
          checkC(results, inputs);
        }

        case FRONT_WINDOW -> {
          results.add(new Token(current));
          checkD(results, inputs);
        }
        
        
        default -> throw new IllegalStateException("Unexpected kind after REAR_WINDOW or FRONT_WINDOW: " + current);
      }
    }

    private void checkD(List<Token> results, Iterator<Kind> inputs) {
      if (!inputs.hasNext()) {
        throw new IllegalStateException("Expected more input after FRONT_WINDOW");
      }

      final Kind current = inputs.next();
      switch (current) {
        case BLOCK -> {
          results.add(new Token(current));
          checkD(results, inputs);
        }

        case BLOCK_WITH_WHEEL -> {
          results.add(new Token(current));
          checkE(results, inputs);
        }
        
        default -> {

        }
      }
    }

    private void checkE(List<Token> results, Iterator<Kind> inputs) {
      if (!inputs.hasNext())
        return;
    }

    private List<String> splitInput() {
      final List<String> list = new ArrayList<>(input.length() / 2);
      for (int i = 0; i < input.length(); i += 2) {
        final int end = Math.min(i + 2, input.length());
        list.add(input.substring(i, end));
      }

      return list;
    }

    public record Token(Kind kind) {
    }
  }
  

  enum Kind {
    BLOCK("block", "bl"),
    BLOCK_WITH_WHEEL("blockmitrad", "br"),
    FRONT_WINDOW("frontscheibe","fs"),
    REAR_WINDOW("heckscheibe", "hs"),
    DOOR("tuer", "tu"),
    WINDOW("fenster", "fe"),
    SPOILER("spoiler", "sp");

    public static final List<String> shortTerms =
        Arrays.stream(values()).map(kind -> kind.shortTerm).toList();

    final String term;
    final String shortTerm;

    Kind(String term, String shortTerm) {
      this.term = term;
      this.shortTerm = shortTerm;
    }

    public static Kind fromShortTerm(String shortTerm) {
      return Arrays.stream(values())
          .filter(kind -> kind.shortTerm.equals(shortTerm))
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException("Unknown short term: " + shortTerm));
    }
  }
}
