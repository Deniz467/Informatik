import java.util.ArrayList;
import java.util.List;

public class HierarchyTraversal {
  public static void main(String[] args) {
    final BinaryTree<String> tree = createTree();
    final List<List<String>> result = new ArrayList<>();
    hierarchyOrder(tree, 0, result);


    System.out.println(result);
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

  private static void hierarchyOrder(BinaryTree<String> tree, int level,
      List<List<String>> result) {

    if (result.size() <= level) {
      result.add(level, new ArrayList<>());
    }

    final List<String> levelResult = result.get(level);

    if (tree.hasItem()) {
      levelResult.add(tree.getItem());
    }

    if (tree.hasLeft()) {
      hierarchyOrder(tree.getLeft(), level + 1, result);
    }

    if (tree.hasRight()) {
      hierarchyOrder(tree.getRight(), level + 1, result);
    }
  }
}
