import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BinaryTreeTraversal {

  public static void main(String[] args) throws NoSuchAlgorithmException {
    final SecureRandom random = SecureRandom.getInstanceStrong();
    final List<Integer> numbers = random.ints(0, 100).limit(100).boxed().collect(Collectors.toList());


    System.out.println("Erstelle Baum mit: " + numbers);

    final BinaryTree<Integer> tree = createTree(numbers);
    final List<Integer> inOrder = BinaryTreeTraversals.inOrder(tree);
    final List<Integer> preOrder = BinaryTreeTraversals.preOrder(tree);
    final List<Integer> postOrder = BinaryTreeTraversals.postOrder(tree);

    System.out.println("In-Oder: " + inOrder);
    System.out.println("Pre-Oder: " + preOrder);
    System.out.println("Post-Order: " + postOrder);

  }


  private static BinaryTree<Integer> createTree(List<Integer> sortedNumbers) {
    sortedNumbers = new ArrayList<>(sortedNumbers);
    final BinaryTree<Integer> tree = new BinaryTree<>();
    final Integer median = sortedNumbers.remove(sortedNumbers.size() / 2);

    tree.setItem(median);


    for (Integer number : sortedNumbers) {
      BinaryTree<Integer> currentTree = tree;

      while (currentTree.hasItem()) {
        if (number < currentTree.getItem()) {
          if (currentTree.hasLeft()) {
            currentTree = currentTree.getLeft();
          } else {
            currentTree.setLeft(new BinaryTree<>(number));
            break;
          }

        } else {
          if (currentTree.hasRight()) {
            currentTree = currentTree.getRight();
          } else {
            currentTree.setRight(new BinaryTree<>(number));
            break;
          }
        }
      }

    }

    return tree;
  }
}

