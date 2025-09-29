import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RandomBinaryTree {

  public static void main(String[] args) throws NoSuchAlgorithmException {
    final SecureRandom random = SecureRandom.getInstanceStrong();
    final List<SearchResult> results = new ArrayList<>();

    for (int i = 100; i < 1000; i += 100) {
      final int finalI = i;
      final List<Integer> randomList = createRandomList(random, i);
      final BinaryTree<Integer> tree = createRandomTree(randomList);
      final List<Integer> linearSearchResults = new ArrayList<>();
      final List<Integer> binarySearchResults = new ArrayList<>();

      random.ints(0, i).limit(10).forEach((number) -> {
        linearSearchResults.add(linearSearch(randomList, number));
        linearSearchResults.add(binarySearchPreorder(tree, number, finalI));
      });

      results.add(new SearchResult(i, linearSearchResults, binarySearchResults));
    }

    printResults(results);
  }

  private static void printResults(List<SearchResult> results) {
    final StringBuilder table = new StringBuilder();

    table.append(String.format("%-50s %-10s %-5s%n", "Elemente",
        "durchschnittliche Vergleiche lineare Suche", "durchschnittliche Vergleiche binäre Suche"));


    for (SearchResult result : results) {
      table.append(String.format("%-5s %-10s %-5s%n", result.elementCount,
          result.averageBinarySearch(), result.averageLinearSearch()));
    }

    System.out.print(table.toString());
  }

  private static int linearSearch(List<Integer> list, int number) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) == number) {
        return i;
      }
    }

    return list.size() + 1;
  }

  private static int binarySearchPreorder(BinaryTree<Integer> tree, int number,
      int searchOperations) {
    if (tree.hasItem()) {
      searchOperations++;
      if (tree.getItem() == number) {
        return searchOperations;
      }
    }

    if (tree.hasLeft()) {
      searchOperations += binarySearchPreorder(tree.getLeft(), number, searchOperations);
    }

    if (tree.hasRight()) {
      searchOperations += binarySearchPreorder(tree.getRight(), number, searchOperations);
    }

    return searchOperations;
  }


  private static List<Integer> createRandomList(SecureRandom random, int amount) {
    return random.ints(0, amount).limit(amount).boxed().collect(Collectors.toList());
  }

  private static BinaryTree<Integer> createRandomTree(List<Integer> numbers) {
    final BinaryTree<Integer> tree = createTree(numbers);
    return tree;
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


  private static class SearchResult {
    final int elementCount;
    final List<Integer> linearSearch;
    final List<Integer> binarySearch;


    SearchResult(int elementCount, List<Integer> linearSearch, List<Integer> binarySearch) {
      this.elementCount = elementCount;
      this.linearSearch = linearSearch;
      this.binarySearch = binarySearch;
    }


    double averageLinearSearch() {
      return average(linearSearch);
    }

    double averageBinarySearch() {
      return average(binarySearch);
    }

    private double average(List<Integer> list) {
      int sum = 0;
      for (Integer element : list) {
        sum += element;
      }

      return (double) sum / list.size();
    }
  }
}
