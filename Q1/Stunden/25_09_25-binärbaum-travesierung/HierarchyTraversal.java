import java.util.ArrayList;
import java.util.List;

public class HierarchyTraversal {
  public static void main(String[] args) {
    final BinaryTree<String> tree = createTree();
    final List<BinaryTree<String>> deepest = new ArrayList<>();
    deepest.add(tree);


    final List<BinaryTree<String>> subTrees = new ArrayList<>();
    int depth = 0;
    while (true) {
      subTrees.clear();

      for (BinaryTree<String> child : deepest) {
        if (child.hasLeft()) {
          subTrees.add(child.getLeft());

        }

        if (child.hasRight()) {
          subTrees.add(child.getRight());
        }
      }

      depth++;

      if (subTrees.isEmpty()) {
        break;
      }

      deepest.clear();
      deepest.addAll(subTrees);
    }
    
    for (BinaryTree<String> deep : deepest) {
      
    }
    
  }

  private static BinaryTree<String> createTree() {
    final var management1 = new BinaryTree<>("Management 1");
    final var management2 = new BinaryTree<>("Management 2");
    final var management3 = new BinaryTree<>("Management 3");
    final var management4 = new BinaryTree<>("Management 4");
    final var board1 = new BinaryTree<>("Board 1");
    final var board2 = new BinaryTree<>("Board 2");
    final var boss = new BinaryTree<>("Boss");

    board1.setLeft(management1);
    board1.setRight(management2);
    board2.setLeft(management3);
    board2.setRight(management4);

    boss.setLeft(board1);
    boss.setRight(board2);

    return boss;
  }
}
