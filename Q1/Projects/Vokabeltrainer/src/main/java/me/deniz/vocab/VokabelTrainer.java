package me.deniz.vocab;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;
import me.deniz.vocab.type.Vokabel;
import me.deniz.vocab.type.VokabelListe;

public class VokabelTrainer {

  public static final SecureRandom RANDOM;

  static {
    SecureRandom temp;
    try {
      temp = SecureRandom.getInstanceStrong();
    } catch (NoSuchAlgorithmException e) {
      temp = new SecureRandom();
    }
    RANDOM = temp;
  }

  private VokabelListe vocabQueue;
  private VocabMode vocabMode;

  public void reset() {
    vocabQueue = VocabRegistry.generateVocabQueue();
  }

  public void prepare(Scanner scanner) {
    reset();

    selectVocabMode(scanner);
  }

  private void selectVocabMode(Scanner scanner) {
    System.out.println("Wähle einen Abfrage-Modus:");
    System.out.println("1. Deutsch -> Englisch");
    System.out.println("2. Englisch -> Deutsch");
    System.out.println("3. Zufällig");

    switch (scanner.nextInt()) {
      case 1:
        this.vocabMode = VocabMode.GERMAN_TO_ENGLISH;
        break;
      case 2:
        this.vocabMode = VocabMode.ENGLISH_TO_GERMAN;
        break;
      case 3:
        this.vocabMode = VocabMode.RANDOM;
        break;
      default:
        System.out.println("Ungültige Eingabe!");
        selectVocabMode(scanner);
    }

    scanner.nextLine();
  }

  public void start(Scanner scanner) {
    if (vocabQueue == null) {
      throw new IllegalStateException("Vocabs not prepared!");
    }

    if (!vocabQueue.hasNextElement()) {
      System.err.println("Keine Vokabeln vorhanden!");
      return;
    }

    while (vocabQueue.hasNextElement()) {
      nextVocab(scanner);
    }

    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    System.out.print("Alle Vokabeln abgefragt! Lernen fortsetzen? (j/n): ");

    String input = scanner.nextLine().trim();
    while (!input.equalsIgnoreCase("j") && !input.equalsIgnoreCase("n")) {
      System.out.println("Ungültige Eingabe! Bitte 'j' für ja oder 'n' für nein eingeben.");
      input = scanner.nextLine().trim();
    }

    if (input.equalsIgnoreCase("j")) {
      recalculateVocabs();
      start(scanner);
    } else {
      System.out.println("Lernen beendet!");
    }
  }

  private void nextVocab(Scanner scanner) {
    final Vokabel vocab = vocabQueue.poll();
    final VocabMode vocabMode = this.vocabMode.getRandomOrCurrentMode();

    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + vocab.getQuestion(vocabMode));
    final String answer = scanner.nextLine();

    if (vocab.isCorrect(answer, vocabMode)) {
      System.out.println("Richtig!");
      vocab.decreaseProbabilityOnSuccess();

      try {
        Thread.sleep(300);
      } catch (InterruptedException ignored) {
      }
    } else {
      System.out.println("Falsch! " + vocab.getAnswer(vocabMode));
      vocab.increaseProbabilityOnFailure();

      System.out.println("Weiter mit Enter...");
      scanner.nextLine();
    }
  }

  public void interrupt() {
    System.out.println("Vokabeltrainer unterbrochen!");

    vocabQueue = null;
  }

  private void recalculateVocabs() {
    this.vocabQueue = VocabRegistry.generateVocabQueue();

    for (final Vokabel vocab : vocabQueue) {
      final float probability = vocab.getProbability();

      if (probability >= Vokabel.APPEAR_MULTIPLE_TIMES_PROBABILITY) {
        final int additionalOccurrences = (int) (probability * 4);
        for (int i = 0; i < additionalOccurrences; i++) {
          vocabQueue.queue(vocab);
        }
      }
    }

    vocabQueue.shuffle();
  }
}
