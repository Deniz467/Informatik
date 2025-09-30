import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class RandomBinaryTree {

  public static void main(String[] args) throws NoSuchAlgorithmException {
    final SecureRandom random = SecureRandom.getInstanceStrong();
    final List<SearchResult> results = new ArrayList<>();

    for (int i = 100; i < 1000; i += 100) {
      final List<Integer> randomList = createRandomList(random, i);
      final BinaryTree<Integer> tree = createRandomTree(randomList);
      final List<Integer> linearSearchResults = new ArrayList<>();
      final List<Integer> binarySearchResults = new ArrayList<>();
      final int[] randomSearchValues = random.ints(0, i).limit(10).toArray();
  
      for (int number : randomSearchValues) {
        linearSearchResults.add(linearSearch(randomList, number));
        binarySearchResults.add(binarySearchPreorder(tree, number, i));      
      }

      results.add(new SearchResult(i, linearSearchResults, binarySearchResults, randomSearchValues));
    }

    printResults(results);
  }

  private static void printResults(List<SearchResult> results) {
    final StringBuilder table = new StringBuilder();

    table.append(String.format("%-15s %-30s %-30s% %-30s%n", "Elemente", "⌀ Vergleiche lineare Suche",
        "⌀ Vergleiche binäre Suche", "Gesuchte Zahlen"));


    for (SearchResult result : results) {
      table.append(String.format("%-15s %-30s %-30s% %-30s%n", result.elementCount,
          result.formattedAverageLinearSearch(), result.formattedAverageBinarySearch(), result.formattedRandomSearchValues()));
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
    if (!tree.hasItem()) {
      return searchOperations;
    }

    searchOperations++;
    if (tree.getItem() == number) {
      return searchOperations;
    }
    if (tree.hasLeft()) {
      int leftResult = binarySearchPreorder(tree.getLeft(), number, searchOperations);
      if (leftResult > searchOperations) { // element found
        return leftResult;
      }
    }
    if (tree.hasRight()) {
      int rightResult = binarySearchPreorder(tree.getRight(), number, searchOperations);
      if (rightResult > searchOperations) {
        return rightResult;
      }
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
    final int[] randomSearchValues;


    SearchResult(int elementCount, List<Integer> linearSearch, List<Integer> binarySearch, int[] randomSearchValues) {
      this.elementCount = elementCount;
      this.linearSearch = linearSearch;
      this.binarySearch = binarySearch;
      this.randomSearchValues = randomSearchValues;
    }


    double averageLinearSearch() {
      return average(linearSearch);
    }

    double averageBinarySearch() {
      return average(binarySearch);
    }

    String formattedAverageLinearSearch() {
      return NumberFormat.getInstance(Locale.GERMAN).format(averageLinearSearch());
    }

    String formattedAverageBinarySearch() {
      return NumberFormat.getInstance(Locale.GERMAN).format(averageBinarySearch());
    }

    String formattedRandomSearchValues() {
      return Arrays.toString(randomSearchValues);
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
