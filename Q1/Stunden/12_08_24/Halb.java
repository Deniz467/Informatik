import java.util.Scanner;

public class Halb {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        System.out.print("Gib eine Zahl ein (Dezimalzahl mit Punkt!):  ");

        double x = scanner.nextDouble();
        System.out.print(">> Der Funktionswert an der Stelle ist: ");
        System.out.println(f(x));

        System.out.println(">> Feststellung von Vorzeichenwechseln.");
        System.out.println(">> Du musst zwei x-Werte eingeben, das Programm prüft, ob f einen VZW hat.");
        System.out.print("Gib den ersten x-Wert ein: ");
        double x1 = scanner.nextDouble();
        System.out.print("Gib den zweiten x-Wert ein: ");
        double x2 = scanner.nextDouble();

        if (f(x1) < 0.0 && f(x2) > 0.0) {
            System.out.println(">> f hat einen Vorzeichenwechsel von - nach + !");
        } else if (f(x1) > 0.0 && f(x2) < 0.0) {
            System.out.println(">> f hat einen Vorzeichenwechsel von + nach - !");
        } else {
            System.out.println(">> f hat keinen Vorzeichenwechsel vorliegen!");
        }

    } // end of main


    static double f(double x) {
        return x * x - 3;
    }

} // end of class Programm1
