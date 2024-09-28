package me.deniz.vocab;

import java.util.concurrent.ThreadLocalRandom;

public enum VocabMode {
  GERMAN_TO_ENGLISH("Deutsch -> Englisch"),
  ENGLISH_TO_GERMAN("Englisch -> Deutsch"),
  RANDOM("Zufällig");

  private final String display;

  VocabMode(String display) {
    this.display = display;
  }

  public String getDisplay() {
    return display;
  }

  public VocabMode getRandomOrCurrentMode() {
    if (this.equals(RANDOM)) {
      return ThreadLocalRandom.current().nextBoolean() ? GERMAN_TO_ENGLISH : ENGLISH_TO_GERMAN;
    }

    return this;
  }
}
