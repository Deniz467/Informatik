import java.util.Arrays;

public class BubbleSort {
    public static void main(String[] args) {
        final int[] toSort = new int[]{4, 8, 3, 1, 9, 2};
        int length = toSort.length;

        while (length > 1) {
            for (int i = 0; i <= length - 2; i++) {
                if (toSort[i] > toSort[i + 1]) {
                    final int temp = toSort[i];
                    toSort[i] = toSort[i + 1];
                    toSort[i + 1] = temp;
                    System.out.println(Arrays.toString(toSort));
                }
            }
            length--;
        }
        

        System.out.println(Arrays.toString(toSort));
    }
}
