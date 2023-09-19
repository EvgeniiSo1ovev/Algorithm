package heapSort;

public class MyHeapSort {
    public void sort(int[] array) {
        int size = array.length;
        for (int i = size / 2 - 1; i >= 0; i--) {
            myHeapify(array, size, i);
        }

        for (int i = size - 1; i >= 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            myHeapify(array, i, 0);
        }
    }

    public void myHeapify(int[] array, int size, int i) {
        int max = i;
        int left = i * 2 + 1;
        int right = i * 2 + 2;
        if (left < size && array[left] > array[max]) {
            max = left;
        }
        if (right < size && array[right] > array[max]) {
            max = right;
        }
        if (max != i) {
            int temp = array[i];
            array[i] = array[max];
            array[max] = temp;
            myHeapify(array, size, max);
        }
    }
}
