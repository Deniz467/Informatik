import java.security.SecureRandom;
import java.util.random.RandomGenerator;

public class ConwaysGameOfLife {

  private static final Slot[][] grid = new Slot[25][100];

  public static void main(String[] args) throws Exception {
    SecureRandom random = SecureRandom.getInstanceStrong();
    fillGrid(random);

    printGrid();
    
    for (int i = 0; i < 100; i++) {
      applyRules();
      printGrid();
      Thread.sleep(100L);
    }
      
    
    printGrid();
    }
    
    private static void fillGrid(RandomGenerator random) {
    /*
     * for (int x = 0; x < grid.length; x++) { for (int y = 0; y < grid[x].length; y++) { grid[x][y]
     * = new Slot(random.nextBoolean(), x, y); } }
     */

    grid[10][49] = new Slot(true, 10, 49);
    grid[10][50] = new Slot(true, 10, 50);
    grid[10][51] = new Slot(true, 10, 51);
    grid[11][49] = new Slot(true, 11, 49);
    grid[11][51] = new Slot(true, 11, 51);
    grid[12][49] = new Slot(true, 12, 49);
    grid[12][51] = new Slot(true, 12, 51);
    grid[14][49] = new Slot(true, 14, 49);
    grid[14][51] = new Slot(true, 14, 51);
    grid[15][49] = new Slot(true, 15, 49);
    grid[15][51] = new Slot(true, 15, 51);
    grid[16][49] = new Slot(true, 16, 49);
    grid[16][50] = new Slot(true, 16, 50);
    grid[16][51] = new Slot(true, 16, 51);

    for (int x = 0; x < grid.length; x++) {
      for (int y = 0; y < grid[x].length; y++) {
        if (grid[x][y] == null) {
          grid[x][y] = new Slot(false, x, y);
        }
      }
    }

    System.out.println(countNeighbors(grid, 11, 50));
  }

  private static Slot[][] applyRules() {
    Slot[][] copy = grid.clone();

    for (int x = 0; x < grid.length; x++) {
      for (int y = 0; y < grid[x].length; y++) {
        Slot current = grid[x][y];
        int neighbors = countNeighbors(grid, x, y);

        if (current.isAlive()) {
          switch (neighbors) {
            case 1 -> current.setAlive(false);
            case 2, 3 -> {

            }
            case 4 -> current.setAlive(false);
          }
        } else {
          if (neighbors > 3) {
            current.setAlive(true);
          }
        }
      }
    }

    return copy;
  }

  private static int[][] directions =
      {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

  private static int countNeighbors(Slot[][] grid, int x, int y) {
    int count = 0;
    for (int[] dir : directions) {
      int neighborX = x + dir[0];
      int neighborY = y + dir[1];
      if (neighborX >= 0 && neighborX < grid.length && neighborY >= 0
          && neighborY < grid[x].length) {
        Slot neighbor = grid[neighborX][neighborY];
        if (neighbor.isAlive()) {
          count++;
        }
      }
    }

    return count;
  }

  private static void printGrid() {
    final StringBuilder sb = new StringBuilder();

    for (Slot[] vertical : grid) {
      sb.append(System.lineSeparator());
      for (Slot current : vertical) {
        sb.append(current.getChar());
      }
    }

    System.out.println(sb);
  }

  public static class Slot {
    private boolean alive;
    private int x;
    private int y;

    public Slot(boolean alive, int x, int y) {
      this.alive = alive;
      this.x = x;
      this.y = y;
    }

    public char getChar() {
      return alive ? '■' : '•';
    }

    public boolean isAlive() {
      return alive;
    }

    public void setAlive(boolean alive) {
      this.alive = alive;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }
  }

}
