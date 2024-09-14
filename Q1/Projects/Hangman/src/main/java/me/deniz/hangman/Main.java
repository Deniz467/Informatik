package me.deniz.hangman;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Main {

  private static final Random RANDOM = new Random();
  private static final List<String> HANGMAN_WORDS = List.of(
      "Hangman",
      "Java",
      "Programmieren",
      "Computer",
      "Software"
  );

  public static void main(String[] args) {
    final Scanner scanner = new Scanner(System.in);
    printCommands();
    while (scanner.hasNextLine()) {
      final String cmd = scanner.nextLine();
      final String cmdLowerCase = cmd.toLowerCase();

      if (cmdLowerCase.equals("exit")) {
        return;
      } else if (cmdLowerCase.startsWith("start")) {
        final String[] cmdArgs = cmd.split(" ");

        if (cmdArgs.length > 2) {
          printUnknownCommand();
          return;
        }

        final String customSolution = getOrNull(cmdArgs, 1);

        startGame(scanner, customSolution);
        printCommands();
      } else {
        if (!cmd.isEmpty()) {
          printUnknownCommand();
        }
      }
    }
  }

  private static void printUnknownCommand() {
    System.err.println("Unknown command");
  }

  private static <T> T getOrNull(T[] array, int index) {
    try {
      return array[index];
    } catch (IndexOutOfBoundsException ignored) {
      return null;
    }
  }

  private static void printCommands() {
    System.out.print("[start [<solution>]/exit]: ");
  }

  private static void startGame(Scanner scanner, String solution) {
    try {
      if (solution == null) {
        solution = HANGMAN_WORDS.get(RANDOM.nextInt(HANGMAN_WORDS.size()));
      }

      final HangmanGame currentGame = new HangmanGame(solution, scanner, System.out, System.err);
      currentGame.prepare();

      while (scanner.hasNext()) {
        currentGame.handleInput(scanner.next());

        if (currentGame.finished()) {
          break;
        }
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static class HangmanGame {

    private static final List<String> HANGMAN = List.of(
        "  +---+",
        "  |   |",
        "  O   |",
        " /|\\  |",
        " / \\  |",
        "      |",
        "========="
    );

    private final PrintStream console;
    private final PrintStream error;

    private final String solution;
    private final String lowerCaseSolution;
    private final Set<Character> guessedLetters;
    private final int uniqueSolutionChars;
    private final int hangmanOffset;
    private int mistakes = 0;

    public HangmanGame(String solution, Scanner scanner, PrintStream console, PrintStream error) {
      this.solution = solution;
      this.lowerCaseSolution = solution.toLowerCase();
      this.console = console;
      this.error = error;
      this.guessedLetters = new HashSet<>();
      this.hangmanOffset = solution.length() * 2 + 5;
      this.uniqueSolutionChars = (int) solution.toLowerCase().chars().distinct().count();
    }

    public void prepare() {
      printUnderscores(solution.length());
    }

    public void handleInput(String input) {
      if (input.length() != 1) {
        updateDisplay("Bitte gebe einen einzelnen Buchstaben ein.");
        return;
      }

      final char letter = input.charAt(0);
      final char lowerCaseLetter = Character.toLowerCase(letter);

      if (guessedLetters.contains(lowerCaseLetter)) {
        updateDisplay("Diesen Buchstaben hast du bereits geraten.");
        return;
      }

      if (lowerCaseSolution.contains(String.valueOf(lowerCaseLetter))) {
        guessedLetters.add(lowerCaseLetter);
        updateDisplay("Richtig geraten!");
      } else {
        mistakes++;
        updateDisplay("Falsch geraten!");
      }

      if (mistakes >= HANGMAN.size()) {
        error.println("Du hast verloren! Die Lösung war: '" + solution + "'");
        return;
      }

      if (guessedLetters.size() == uniqueSolutionChars) {
        console.println("Herzlichen Glückwunsch! Du hast gewonnen!");
      }
    }

    private void updateDisplay(String additionalMessage) {
      final StringBuilder sb = new StringBuilder();

      sb.append(System.lineSeparator().repeat(50));

      final int mistakesLeft = HANGMAN.size() - mistakes;
      sb.append("[Hangman Game]: ");
      sb.append("Noch ");
      sb.append(mistakesLeft);
      sb.append(" Versuch");
      if (mistakesLeft != 1) {
        sb.append("e");
      }
      sb.append(System.lineSeparator());

      if (additionalMessage != null) {
        sb.append("[Hangman Game]: ");
        sb.append(additionalMessage);
        sb.append(System.lineSeparator());
      }

      buildHangman(sb, mistakes);
      buildHangmanUnderscores(sb);

      console.println(sb);
    }

    private void buildHangmanUnderscores(StringBuilder sb) {
      for (final char solutionChar : solution.toCharArray()) {
        if (guessedLetters.contains(Character.toLowerCase(solutionChar))) {
          sb.append(solutionChar).append(" ");
        } else {
          sb.append("_ ");
        }
      }
    }

    public boolean finished() {
      return mistakes >= HANGMAN.size() || guessedLetters.size() == uniqueSolutionChars;
    }

    private void printUnderscores(int amount) {
      console.println("_ ".repeat(amount));
    }

    private void buildHangman(StringBuilder sb, int mistakes) {
      final int safeMistakes = Math.min(mistakes, HANGMAN.size());

      for (int i = 0; i < safeMistakes; i++) {
        sb.append(" ".repeat(hangmanOffset))
            .append(HANGMAN.get(i))
            .append(System.lineSeparator());
      }
    }
  }
}