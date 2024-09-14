import java.util.Arrays;

public class InsertionSort {
    public static void main(String[] args) {
        final int[] toSort = new int[]{4, 8, 3, 1, 9, 2};
        insertionSort(toSort);
        System.out.println(Arrays.toString(toSort));

    }

    private static void insertionSort(int[] toSort) {
        for (int i = 1; i < toSort.length; i++) {
            int temp = toSort[i];
            int j = i;

            while (j > 0 && toSort[j - 1] > temp) {
                toSort[j] = toSort[j - 1];
                j--;
            }
            toSort[j] = temp;
        }
    }
}
