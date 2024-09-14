public class Funktionsaufrufe {

    public static void main(String[] args) {
        System.out.println("Zahlen vor und nach dem Funktionsaufruf!");

        int zahl = 5;
        System.out.println("Die Zahl ist: " + zahl);
        erhoehe(zahl);
        System.out.println("Die Zahl nach Funktionsaufruf ist: " + zahl);

        int[] array = new int[5]; //Lege ein Array mit Integer an
        for (int i = 0; i < 5; i++) {
            array[i] = i;
        }

        System.out.println("Das Feld vor dem Funktionsaufruf: ");
        ausgabe(array);

        erhoeheArray(array);
        System.out.println("Das Feld nach dem Funktionsaufruf: ");
        ausgabe(array);

    } // end of main

    static void erhoehe(int x) {
        x = x + 1;
        System.out.println("Die Zahl in der Funktion ist: " + x);
    }

    static void erhoeheArray(int[] xarray) {
        for (int i = 0; i < xarray.length; i++) {
            xarray[i] = xarray[i] + 1;
        }
        System.out.println("Das Feld in der Funktion: ");
        ausgabe(xarray);
    }

    static void ausgabe(int[] xarray) {
        for (int i = 0; i < xarray.length; i++) {
            System.out.print(xarray[i] + "  ");
        }
        System.out.println("");
    }

}