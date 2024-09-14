import java.util.Arrays;

public class SelectionSort {
    public static void main(String[] args) {
        final int[] toSort = new int[]{4, 8, 3, 1, 9, 2};
        int left = 0;
        int length = toSort.length;
        int min;

        while (left < length) {
            min = left;

            for (int i = left + 1; i < length; i++) {
                if (toSort[i] < toSort[min]) {
                    min = i;
                }
            }

            final int temp = toSort[min];
            toSort[min] = toSort[left];
            toSort[left] = temp;

            left++;

            System.out.println(Arrays.toString(toSort));
        }

        System.out.println(Arrays.toString(toSort));
    }
}
