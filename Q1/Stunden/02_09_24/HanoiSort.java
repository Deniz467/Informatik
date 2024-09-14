import java.util.Scanner;

public class HanoiSort {

  public static void main(String[] args) {
    final Scanner scanner = new Scanner(System.in);

    System.out.print(">> Gib die Größe des Hanoi-Towers ein: ");
    while (scanner.hasNext()) {
      moveTower(scanner.nextInt());
      System.out.print("\n\n>> Gib die Größe des Hanoi-Towers ein: ");
    }
  }

  private static void moveTower(int height) {
    System.out.println("\n\nTower of Hanoi " + height);
    moveTower(height, 'A', 'B', 'C');
  }

  private static void moveTower(int height, char start, char help, char goal) {
    if (height == 1) {
      System.out.println(start + " -> " + goal);
      return;
    }

    moveTower(height - 1, start, goal, help);
    System.out.println(start + " -> " + goal);
    moveTower(height - 1, help, start, goal);
  }
}
