import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BinaryTreeTraversals {


  public static List<Integer> inOrder(BinaryTree<Integer> tree) {
    final List<Integer> result = new ArrayList<>();

    if (tree.hasLeft()) {
      result.add(tree.getLeft().getItem());
      result.addAll(inOrder(tree.getLeft()));
    }

    result.add(tree.getItem());

    if (tree.hasRight()) {
      result.add(tree.getRight().getItem());
      result.addAll(inOrder(tree.getRight()));
    }


    return result;
  }


  public static List<Integer> preOrder(BinaryTree<Integer> tree) {
    final List<Integer> result = new ArrayList<>();

    result.add(tree.getItem());

    if (tree.hasLeft()) {
      result.add(tree.getLeft().getItem());
      result.addAll(inOrder(tree.getLeft()));
    }

    if (tree.hasRight()) {
      result.add(tree.getRight().getItem());
      result.addAll(inOrder(tree.getRight()));
    }


    return result;
  }
}
