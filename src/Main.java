import heapSort.MyHeapSort;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[] array = getArray(new int[21]);
        printArray(array);
        MyHeapSort myHeapSort = new MyHeapSort();
        myHeapSort.sort(array);
        printArray(array);
    }

    public static int[] getArray(int[] array) {
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(100);
        }
        return array;
    }

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}