package me.deniz.vocab.type;

public class Vokabel {

  private final String german;
  private final String english;
  private float probability = 1.0f;

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
    probability = Math.min(1.0f, probability + 0.1f);
  }

  public void decreaseProbabilityOnSuccess() {
    probability = Math.max(0.0f, probability - 0.1f);
  }

  @Override
  public String toString() {
    return "Vokabel{" +
        "english='" + english + '\'' +
        ", german='" + german + '\'' +
        '}';
  }
}
