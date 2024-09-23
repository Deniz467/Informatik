public class Druckauftrag {

  private final String inhalt;

  public Druckauftrag(String inhalt) {
    this.inhalt = inhalt;
  }

  public String getInhalt() {
    return inhalt;
  }

  @Override
  public String toString() {
    return inhalt;
  }
}