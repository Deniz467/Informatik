
import java.util.ArrayList;
import java.util.List;

public class Grammatik {

  public static void main(String[] args) {
    var g = new Grammatik()
    .addNonTerminal("S")
    .addNonTerminal("A")
    .addNonTerminal("B")
    .addProduktion(List.of("laA", "leA", "luA"))
    .addProduktion(List.of("laB", "leB", "luB"))
    .addProduktion(List.of("lu"))
    .validate();

  }

  private final List<String> nonTerminals = new ArrayList<>();
  private final List<List<String>> produktionen = new ArrayList<>();

  Grammatik addNonTerminal(String nonTerminal) {
    nonTerminals.add(nonTerminal);
    return this;
  }

  Grammatik addProduktion(List<String> produktion) {
    produktionen.add(produktion);
    return this;
  }

  Grammatik validate() {
    for (var produktion : produktionen) {
      for (var symbol : produktion) {
        if (!nonTerminals.contains(symbol)) {
          throw new IllegalStateException("Ungültige Produktion: " + produktion);
        }
      }
    }
    return this;
  }

  String generateRandomWord() {
    var word = new StringBuilder(nonTerminals.getFirst());
    var finished = false;
    var nonTerminalIdx = word.length() - 1;
    
    while (!finished) {
      var nonTerminal = word.charAt(nonTerminalIdx);
      
    }

    return word.toString();
  }
}
