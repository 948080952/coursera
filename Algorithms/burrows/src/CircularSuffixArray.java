
/**
 * Created by daipei on 2017/11/24.
 */

public class CircularSuffixArray {

    private int length = 0;
    private String string;
    private int index[];

    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        length = s.length();
        string = s;
        index = new int[length];
        for (int i = 0; i < length; i++) {
            index[i] = i;
        }
        quicksort(index);
    }

    private boolean less(int i, int j) {
        int i1, j1;
        for (int k = 0; k < length; k++) {
            i1 = (i + k) % length;
            j1 = (j + k) % length;
            if (string.charAt(i1) > string.charAt(j1)) {
                return false;
            }
            if (string.charAt(i1) < string.charAt(j1)) {
                return true;
            }
        }
        return false;
    }

    private void quicksort(int[] array) {
        quicksortCore(array, 0, array.length - 1);
    }

    private void quicksortCore(int[] array, int left, int right) {
        if (left >= right) return;
        int div = partition(array, left, right);
        quicksortCore(array, left, div - 1);
        quicksortCore(array, div + 1, right);
    }

    private int partition(int[] array, int left, int right) {
        int mid = (left + right) / 2;
        int pivot = left;
        int p = left;
        exch(array, mid, right);
        while (p < right) {
            if (less(array[p], array[right])) {
                exch(array, p, pivot);
                pivot++;
            }
            p++;
        }
        exch(array, right, pivot);
        return pivot;
    }

    private void exch(int[] array, int a, int b) {
        int tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    public int length() {
        return length;
    }

    public int index(int i) {
        invalidateIndex(i);
        return index[i];
    }

    private void invalidateIndex(int i) {
        if (i < 0 || i >= length) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        CircularSuffixArray array = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < array.length(); i++) {
            System.out.println(array.index(i));
        }
    }
}
