public class Vokabel {
  private final String german;
  private final String english;

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

  @Override
  public String toString() {
    return "Vokabel{" +
        "english='" + english + '\'' +
        ", german='" + german + '\'' +
        '}';
  }
}
