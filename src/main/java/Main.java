/**
 * Experience with what is the best parameter for the cut off that the optimized merge sort can have.
 */

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Preparations
        int arraySize = 10000000;
        int maxIntegerSize = arraySize;
        int tests = 10;
        long[] averageArray = new long[tests];

        // Test
        for(int i = 0; i < tests; i++) {
            int[] testArray = generateArray(maxIntegerSize, arraySize);
            int[] copyArray = new int[testArray.length];
            System.out.println("Test " + (i + 1));
            for(int j = 0; j < 31; j++) {
                cloneArray(testArray,copyArray);
                long startTime = System.nanoTime();
                MergeAndInsertion.sort(copyArray, j);
                long endTime = System.nanoTime();
                long executionTime = (endTime - startTime) / 1000000;
                averageArray[j] += executionTime;
                System.out.println("The time taken to finish(" + j +") : " + executionTime);
            }
            System.out.println("");
        }
        System.out.println("Average time");
        for(int k = 0; k < averageArray.length; k++) {
            System.out.println("Average time takes (" + k + "): " + averageArray[k] / tests);
        }
    }

    /**
     * Copy an array to new array.
     * @param array the array that wanted to be copied.
     * @param copyArray the copied array that the information will be copied to.
     */
    static void cloneArray(int[] array, int[] copyArray) {
        for(int i = 0; i < array.length; i++) {
            copyArray[i] = array[i];
        }
    }

    /**
     * Generate an array with random integer numbers.
     * @param maxIntegerSize the biggest possible integer that can be generated.
     * @param arraySize how many inputs wanted in the array.
     * @return the random generated array.
     */
    static int[] generateArray(int maxIntegerSize, int arraySize) {
        int[] generateArray = new int[arraySize];
        Random random = new Random();
        for(int i = 0; i < arraySize; i++) {
            generateArray[i] = random.nextInt(maxIntegerSize + 1);
        }
        return generateArray;
    }

    /**
     * Print the content of an array. the style is {x, x, x, ...}
     * @param array which will be printed.
     */
    static void printArray(int[] array) {
        String arrString = "{";
        for(int i = 0; i < array.length; i++) {
            arrString += array[i];
            if(i != array.length -1) {
                arrString += ", ";
            }
            if(i % 10 == 0 && i != 0) {
                arrString += "\n";
            }
        }
        arrString += "}";
        System.out.println(arrString);
    }
}

/**
 * The optimized merge sort which depends on the insertion sort for the small arrays.
 */
class MergeAndInsertion {
    // The smallest array that the merge sort will let the insertion sort handle instead.
    private static int CUTOFF;
    
    private MergeAndInsertion() {}

    /**
     * Will merge two parts of an array that was divided into sections.
     * @param unsortedArray the unsorted array that will sorted using the merge algorithm.
     * @param copyArray array to copy the content of the original array.
     * @param lo the lowest index for the array that wanted to be sorted.
     * @param mid middle index for the array.
     * @param hi the biggest index that the array size provides.
     */
    private static void merge(int[] src, int[] dst, int lo, int mid, int hi) {
        assert isSorted(src, lo, mid);
        assert isSorted(src, mid+1, hi);

        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if(i > mid) {
                dst[k] = src[j++];
            } else if (j > hi) {
                dst[k] = src[i++];
            } else if (less(src[j], src[i])) {
                dst[k] = src[j++];
            } else {
                dst[k] = src[i++];
            }
        }
        assert isSorted(dst, lo, hi);
    }

    /**
     * Will call the sort method and giving the right inputs to it.
     * @param unsortedArray the array that wanted to be sorted.
     */
    public static void sort(int[] unsortedArray, int cutOff) {
        int[] copyArray = unsortedArray.clone();
        CUTOFF = cutOff;
        sort(copyArray, unsortedArray, 0, unsortedArray.length-1);
        assert isSorted(unsortedArray);
    }

    /**
     * Will manage the divisions of the sub arrays and merge them together under the right condition.
     * when they are sorted.
     * @param src the unsorted array that will be sorted.
     * @param dst an array to copy the content of the original array.
     * @param lo the lowest index that the array has.
     * @param hi the highest index that the array provides.
     */
    private static void sort(int[] src, int[] dst, int lo, int hi) {
        if (hi <= lo + CUTOFF) {
            insertionSort(dst, lo, hi);
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(dst, src, lo, mid);
        sort(dst, src, mid+1, hi);

        if (!less(src[mid+1], src[mid])) {
            for (int i = lo; i <= hi; i++) dst[i] = src[i];
            return;
        }
        merge(src, dst, lo, mid, hi);
    }

    /**
     * The insertion sort algorithm which will be used to sort the small arrays depending on the cut off decided.
     * @param unsortedArray the small piece of the original array.
     * @param lo the smallest index in the array.
     * @param hi the highest index that the array can provide.
     */
    static void insertionSort(int[] unsortedArray, int lo, int hi) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(unsortedArray[j], unsortedArray[j - 1]); j--) {
                exchange(unsortedArray, j, j - 1);
            }
        }
    }

    /**
     * Swap between two numbers that are in the array.
     * @param array in which the swap wanted to be.
     * @param i the position for the first number.
     * @param j the position for the second number.
     */
    static void exchange(int[] unsortedArray, int i, int j) {
        int swap = unsortedArray[i];
        unsortedArray[i] = unsortedArray[j];
        unsortedArray[j] = swap;
    }

    /**
     * Check whether the first number is smaller that the second number.
     * @param num1 the first number.
     * @param num2 the second number.
     * @return true of false depending on the condition.
     */
    private static boolean less(int num1, int num2) {
        return num1 < num2;
    }

    /**
     * Checks if the array is sorted or not.
     * @param sortedArray the array which assumed to be sorted.
     * @return false of true depending on the condition.
     */
    private static boolean isSorted(int[] sortedArray) {
        return isSorted(sortedArray, 0, sortedArray.length - 1);
    }

    /**
     *
     * @param sortedArray the assumed sorted array.
     * @param lo the smallest index in the array.
     * @param hi the highest index that the array offers.
     * @return if is it sorted or not.
     */
    private static boolean isSorted(int[] sortedArray, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(sortedArray[i], sortedArray[i-1])) return false;
        return true;
    }
}