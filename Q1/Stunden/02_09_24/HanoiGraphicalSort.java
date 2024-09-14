import java.util.Scanner;

public class HanoiGraphicalSort {

  public static void main(String[] args) {
    final Scanner scanner = new Scanner(System.in);

    /*
    System.out.print(">> Gib die Größe des Hanoi-Towers ein: ");
    while (scanner.hasNext()) {
      moveTower(scanner.nextInt());
      System.out.print("\n\n>> Gib die Größe des Hanoi-Towers ein: ");
    }

     */
//    System.out.println(buildTower(10) + buildTower(10));
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

  private String buildLine(int width, int maxWidth) {
    final StringBuilder sb = new StringBuilder();
    int half = width / 2;
    if (half % 2 != 0) {
      half++;
    }

    final int spaces = (maxWidth / 2) - width;

    sb.append(" ".repeat(spaces));
    sb.append("#".repeat(half));
    sb.append("|");
    sb.append("#".repeat(half));
    sb.append(" ".repeat(spaces));

    return sb.toString();
  }

  /*
  private static String buildTower(int heightFirst, int heightSecond, int heightThird) {
    final StringBuilder sb = new StringBuilder();

    for (int hashTagsFirst = 0; hashTagsFirst < heightFirst + 1; hashTagsFirst++) {
      final int spaces = heightFirst - hashTagsFirst;

      sb.append(" ".repeat(spaces));
      sb.append("#".repeat(hashTagsFirst));
      sb.append("|");
      sb.append("#".repeat(hashTagsFirst));
      sb.append(" ".repeat(spaces));
      sb.append("\n");
    }

    return sb.toString();
  }

   */

//  private static String buildTowers(int[][] firstTower, int[][] secondTower, int[][] thirdTower) {
//    checkSameLength(firstTower.length, secondTower.length, thirdTower.length);
//  }

  private static void checkSameLength(int toCheck, int... length) {
    for (int i : length) {
      if (toCheck != i) {
        throw new IllegalStateException("Different lenghts");
      }
    }
  }

}
