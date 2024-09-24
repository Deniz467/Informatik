package me.deniz.vocab;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;
import me.deniz.vocab.type.Vokabel;

public class VokabelTrainer {

  private static final SecureRandom RANDOM;

  static {
    SecureRandom temp;
    try {
      temp = SecureRandom.getInstanceStrong();
    } catch (NoSuchAlgorithmException e) {
      temp = new SecureRandom();
    }
    RANDOM = temp;
  }

  private Vokabel[] vocabs;


  public void reset() {
    vocabs = VocabRegistry.generateVocabs();
  }

  public void prepare() {
    reset();
  }

  public void start(Scanner scanner, VocabMode vocabMode) {
    if (vocabs == null) {
      throw new IllegalStateException("Vocabs not prepared!");
    }
  }

  public void interrupt() {
    vocabs = null;
  }

  private Vokabel generateRandomVocab() {
    float totalProbability = 0.0f;
    for (final Vokabel vocab : vocabs) {
      totalProbability += vocab.getProbability();
    }

    float random = RANDOM.nextFloat() * totalProbability;  // random number between 0 and 1 multiplied by total probability
    for (final Vokabel vocab : vocabs) {
      random -= vocab.getProbability();
      if (random <= 0.0f) {
        return vocab;
      }
    }

    return vocabs[vocabs.length - 1];
  }
}
