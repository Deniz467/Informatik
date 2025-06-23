import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.random.RandomGenerator;

public class ForestFire  {

  private static final int MAX_X = 50;
  private static final int MAX_Y = 35;
  private static final int P_BURN = 30;
  private static final double P_GROW = 10;

  private static State[][] grid = new State[MAX_Y][MAX_X];

  public static void main(String[] args) throws Exception {
    SecureRandom random = SecureRandom.getInstanceStrong();
    fillGrid(random);
    printGrid();

    
    for (int i = 0; i < 100; i++) {
      grid = applyRules(random);
      printGrid();
      Thread.sleep(500L);
    }
  }

  private static final void printGrid() {
    StringBuilder sb = new StringBuilder();
    for (State[] heightStates : grid) {
      sb.append(System.lineSeparator());
      for (State state : heightStates) {
        sb.append(state.getDisplay());
      }
    }
    
    System.out.println(sb);
  }

  private static State[][] applyRules(RandomGenerator random) {
    State[][] copy = grid.clone();

    forEachField((current, x, y) -> {
      switch (current) {
        case BURNING_TREE -> {
          copy[x][y] = State.BURNING_TREE_EXTINGUISH;
        }

        case BURNING_TREE_EXTINGUISH -> {
          copy[x][y] = State.EMPTY;
        }

        case TREE -> {
          int burningNeighbours = countNeighbors(grid, x, y, List.of(State.BURNING_TREE, State.BURNING_TREE_EXTINGUISH));
          if (burningNeighbours >= 1 && random.nextDouble(0, 100) < P_BURN) {
            copy[x][y] = State.BURNING_TREE;
          }
        }

        case EMPTY -> {
          if (random.nextDouble(0, 100) < P_GROW) {
            copy[x][y] = State.TREE;
          }
        }
      }
    });

    return copy;
  }

  private static final void fillGrid(RandomGenerator random) {
    forEachField((current, x, y) -> {
      if (random.nextDouble(0, 100) < 1) {
        grid[x][y] = State.BURNING_TREE;
      } else {
        grid[x][y] = State.TREE;
      }
    });
  }

  private static void forEachField(Action action) {
    for (int x = 0; x < grid.length; x++) {
      State[] heightStates = grid[x];
      for (int y = 0; y < heightStates.length; y++) {
        action.runAction(grid[x][y], x, y);
      }
    }
  }


  private static int[][] directions =
      {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

  private static int countNeighbors(State[][] grid, int x, int y, List<State> filters) {
    int count = 0;
    for (int[] dir : directions) {
      int neighborX = x + dir[0];
      int neighborY = y + dir[1];
      if (neighborX >= 0 && neighborX < grid.length && neighborY >= 0
          && neighborY < grid[x].length) {
        State neighbor = grid[neighborX][neighborY];
        if (filters.contains(neighbor)) {
          count++;
        }
      }
    }

    return count;
  }

  @FunctionalInterface
  interface Action {
    void runAction(State current, int x, int y);
  }

  
  enum State {
    EMPTY(" "),
    TREE("🌳"),
    BURNING_TREE("🔥"),
    BURNING_TREE_EXTINGUISH("🔥");

    private final String display;

    private State(String display) {
      this.display = display;
    }

    public String getDisplay() {
      return display;
    }
  }
}
