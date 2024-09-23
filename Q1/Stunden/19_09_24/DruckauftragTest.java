/**
 * DruckauftragTest
 */
public class DruckauftragTest {

  public static void main(String[] args) {
    final Druckauftrag auftrag1 = new Druckauftrag("Hallo Welt!");
    final Druckauftrag auftrag2 = new Druckauftrag("Hello World!");
    final Druckauftrag auftrag3 = new Druckauftrag(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
    final Druckauftrag auftrag4 = new Druckauftrag(
        "The quick brown fox jumps over the lazy dog. 0123456789");
    final Druckauftrag auftrag5 = new Druckauftrag("Jackdaws love my big sphinx of quartz.");
    final Druckauftrag auftrag6 = new Druckauftrag(
        "Franz jagt im komplett verwahrlosten Taxi quer durch Bayern.");

    System.out.println("********Test der Druckauftraege!***********");
    System.out.println(auftrag1.getInhalt());
    System.out.println(auftrag2.getInhalt());
    System.out.println(auftrag3.getInhalt());
    System.out.println(auftrag4.getInhalt());
    System.out.println(auftrag5.getInhalt());
    System.out.println(auftrag6.getInhalt());
    System.out.println("********Test Ende********");

    final WaitingLine<Druckauftrag> schlange = new WaitingLine<>();
    System.out.println("Schlange leer? " + !schlange.hasNextElement());
    schlange.queue(auftrag1);
    System.out.println("Schlange leer? " + !schlange.hasNextElement());
    schlange.queue(auftrag2);
    schlange.queue(auftrag4);
    schlange.queue(auftrag6);

    System.out.println("********Drucken aller Auftraege**********");
    System.out.println(buildDisplay(schlange));

    System.out.println("********Erster Auftrag entfernt********");
    schlange.poll();

    System.out.println("********Drucken aller Auftraege**********");
    System.out.println(buildDisplay(schlange));

    System.out.println("********Erster Auftrag entfernt********");
    schlange.poll();

    System.out.println("********Drucken aller Auftraege**********");
    System.out.println(buildDisplay(schlange));

    System.out.println("********Auftraege hinzufuegen********");
    schlange.queue(auftrag5);
    schlange.queue(auftrag3);
    System.out.println("********Drucken aller Auftraege**********");
    System.out.println(buildDisplay(schlange));
    while (schlange.hasNextElement()) {
      schlange.poll();
    }
    System.out.println("Schlange wieder leer " + !schlange.hasNextElement());

  }

  public static String buildDisplay(WaitingLine<Druckauftrag> queue) {
    return queue.toString();
  }

}