/**
 * DruckauftragTest
 */
public class DruckauftragTest {

    public static void main(String[] args) {
        Druckauftrag auftrag1 = new Druckauftrag("Hallo Welt!");
        Druckauftrag auftrag2 = new Druckauftrag("Hello World!");
        Druckauftrag auftrag3 = new Druckauftrag("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        Druckauftrag auftrag4 = new Druckauftrag("The quick brown fox jumps over the lazy dog. 0123456789");
        Druckauftrag auftrag5 = new Druckauftrag("Jackdaws love my big sphinx of quartz.");
        Druckauftrag auftrag6 = new Druckauftrag("Franz jagt im komplett verwahrlosten Taxi quer durch Bayern.");

        System.out.println("********Test der Druckauftraege!***********");
        System.out.println(auftrag1.getInhalt());
        System.out.println(auftrag2.getInhalt());
        System.out.println(auftrag3.getInhalt());
        System.out.println(auftrag4.getInhalt());
        System.out.println(auftrag5.getInhalt());
        System.out.println(auftrag6.getInhalt());
        System.out.println("********Test Ende********");

        final WaitingLine<Druckauftrag> queue = new WaitingLine<>();

        System.out.println("Schlange leer? " + queue.hasNextElement());    
        queue.add(auftrag1);
        System.out.println("Schlange leer? " + queue.hasNextElement());  
        queue.add(auftrag2);
        queue.add(auftrag4);
        queue.add(auftrag6);
    
    }

    public static String alleAusgeben(WaitingLine<Druckauftrag> q1){
        Druckauftrag aktuell = q1.pop();
        String name = "- " +aktuell.getInhalt();
        while (aktuell.getNachfolger()!=null) { 
          name = name + " -\n- " + aktuell.getNachfolger().getInhalt();
          aktuell = aktuell.getNachfolger();
        } // end of while
        return name + " -";
      }
    
}