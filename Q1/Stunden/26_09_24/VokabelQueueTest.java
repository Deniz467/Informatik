public class VokabelQueueTest {

  public static void main(String[] args) {
    final WaitingLine<Vokabel> waitingLine = new WaitingLine<>();

    waitingLine.queue(Vokabel.of("Hund", "dog"));
    waitingLine.queue(Vokabel.of("Katze", "cat"));
    waitingLine.queue(Vokabel.of("Maus", "mouse"));

    System.out.println("Initiale Warteschlange:");
    System.out.println(waitingLine);

    System.out.println("Pop-Ergebnis (nur anzeigen, nicht entfernen):");
    waitingLine.pop().display();
    System.out.println();

    System.out.println("Poll-Ergebnis (entfernen und anzeigen):");
    waitingLine.poll().display();
    System.out.println();

    System.out.println("Warteschlange nach erstem Poll:");
    System.out.println(waitingLine);

    waitingLine.poll();
    waitingLine.poll();

    System.out.println("Warteschlange nach weiteren Polls:");
    System.out.println(waitingLine);

    if (!waitingLine.hasNextElement()) {
      System.out.println("Die Warteschlange ist jetzt leer.");
    }
  }
}
