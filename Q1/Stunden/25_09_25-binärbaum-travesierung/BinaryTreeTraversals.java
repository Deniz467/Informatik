import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BinaryTreeTraversals {


  public static <C> List<C> inOrder(BinaryTree<C> tree) {
    final List<C> result = new ArrayList<>();

    if (tree.hasLeft()) {
      result.addAll(inOrder(tree.getLeft()));
    }

    result.add(tree.getItem());

    if (tree.hasRight()) {
      result.addAll(inOrder(tree.getRight()));
    }


    return result;
  }


  public static <C> List<C> preOrder(BinaryTree<C> tree) {
    final List<C> result = new ArrayList<>();

    result.add(tree.getItem());

    if (tree.hasLeft()) {
      result.addAll(preOrder(tree.getLeft()));
    }

    if (tree.hasRight()) {
      result.addAll(preOrder(tree.getRight()));
    }


    return result;
  }

  public static <C> List<C> postOrder(BinaryTree<C> tree) {
    final List<C> result = new ArrayList<>();


    if (tree.hasLeft()) {
      result.addAll(postOrder(tree.getLeft()));
    }

    if (tree.hasRight()) {
      result.addAll(postOrder(tree.getRight()));
    }

    result.add(tree.getItem());


    return result;
  }
}
