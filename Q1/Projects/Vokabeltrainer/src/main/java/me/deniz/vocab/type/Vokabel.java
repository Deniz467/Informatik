package me.deniz.vocab.type;

import java.util.Objects;
import me.deniz.vocab.VocabMode;

public class Vokabel implements Cloneable {

  public static final float MAX_PROBABILITY = 2.0f;
  public static final float MIN_PROBABILITY = 0.0f;
  public static final float PROBABILITY_STEP = 0.1f;
  public static final float APPEAR_MULTIPLE_TIMES_PROBABILITY = 0.5f;

  private final String german;
  private final String english;
  private float probability = 0.5f; // upper bound is 2.0f and lower bound is 0.0f

  public Vokabel(String german, String english) {
    this.german = german;
    this.english = english;
  }

  public static Vokabel of(String german, String english) {
    return new Vokabel(german, english);
  }

  public void display() {
    System.out.println(german + " | " + english);
  }

  public String getEnglish() {
    return english;
  }

  public String getGerman() {
    return german;
  }

  public float getProbability() {
    return probability;
  }

  public void increaseProbabilityOnFailure() {
    probability = Math.min(MAX_PROBABILITY, probability + PROBABILITY_STEP);
  }

  public void decreaseProbabilityOnSuccess() {
    probability = Math.max(MIN_PROBABILITY, probability - PROBABILITY_STEP);
  }

  public String getQuestion(VocabMode vocabMode) {
    return "Übersetze: '" + (vocabMode == VocabMode.GERMAN_TO_ENGLISH ? german : english) + "'";
  }

  public String getAnswer(VocabMode vocabMode) {
    return "Die richtige Antwort wäre: '" + (vocabMode == VocabMode.GERMAN_TO_ENGLISH ? english : german) + "'";
  }

  public boolean isCorrect(String answer, VocabMode vocabMode) {
    final String correctAnswer = vocabMode == VocabMode.GERMAN_TO_ENGLISH ? english : german;
    final String enhancedAnswer = answer.trim();

    return enhancedAnswer.equalsIgnoreCase(correctAnswer);
  }

  @Override
  public Vokabel clone() {
    try {
      return (Vokabel) super.clone();
    } catch (Exception e) {
      throw new AssertionError(e);
    }
  }

  @Override
  public String toString() {
    return "Vokabel{" +
        "english='" + english + '\'' +
        ", german='" + german + '\'' +
        '}';
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Vokabel)) {
      return false;
    }

    Vokabel vokabel = (Vokabel) o;
    return Objects.equals(getGerman(), vokabel.getGerman()) && Objects.equals(
        getEnglish(), vokabel.getEnglish());
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(getGerman());
    result = 31 * result + Objects.hashCode(getEnglish());
    return result;
  }
}
