import java.util.Scanner;

public class Programm {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        System.out.print("Gebe a ein: ");
        final double a = scanner.nextDouble();
        System.out.println("Gebe b ein: ");
        final double b = scanner.nextDouble();

        if (!hasChangeOfSign(a, b)) {
            System.err.println("Es liegt kein Vorzeichenwechsel zwischen a und b vor!");
            return;
        }

        processNumbers(a, b);
    }

    private static void processNumbers(double a, double b) {
        final double m = (a + b) / 2.0;
        final double fm = f(m);

        if (fm < 0.001) {
            System.out.printf("m = %s f(m) = %s", m, fm);
            return;
        }

        if (hasChangeOfSign(a, m)) {
            processNumbers(a, m);
        } else if (hasChangeOfSign(b, m)) {
            processNumbers(b, m);
        }
    }

    private static boolean hasChangeOfSign(double a, double b) {
        return (f(a) < 0.0 && f(b) > 0.0) || (f(a) > 0.0 && f(b) < 0.0);
    }

    private static double f(double x) {
        return x * x - 3;
    }

}
