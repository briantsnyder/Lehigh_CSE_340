package IntSortingMethods;

import sun.tools.jar.resources.jar_ja;

public class MergeRadixSort extends Sort {
    @Override
    void algorithm() {
        /* Helper Method that checks if array is already sorted, if it's not sorted then my sorting algorithm is run*/
        if (!checkIfSorted(this.data)) {
            sort(this.data);
        }
    }

    /* Helper method that partitions array into positives and negatives, then sorts them and combines them */
    void sort(int[] arr) {

        /* Iteraties through array and determines number of negative values */
        int count = 0;
        for (int i = 0; i < arr.length; i++) { 
            if (arr[i] < 0) {
                count++;
            }
        }

        /* Uses this number of negative values to create an array of negative values and a separate array of positive values */
        int negNums[] = new int[count];
        int posNums[] = new int[arr.length-count];
        
        /* Itertes through data and depending on its value (positive or negative), it puts the value in the appropriate array */
        count = 0;
        int counter2 = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 0) {
                negNums[count] = arr[i];
                count++;
            } else {
                posNums[counter2] = arr[i];
                counter2++;
            }
        }

        /* Performs Merge Sort on the Negative Values */
        int left = 0;
        int right = count - 1;
        helperSort(negNums, left, right);

        /* Performs Radix Sort on the Positive Values */
        int max = getMax(posNums);
        for (int i = 1; max / i > 0; i *= 10) {
            countSort(posNums, counter2, i);
        }

        /* Combines the 2 arrays given that the negative values will always come first before the positive values */
        for (int i = 0; i < arr.length; i++) {
            if (i < count) {
                arr[i] = negNums[i];
            } else {
                arr[i] = posNums[i-count];
            }
        }

        /*
        Old Code that Used Counting Sort

        int max = getMax(arr);
        int min = getMin(arr);
        int range = max - min + 1;
        int count[] = new int[range];
        int output[] = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            count[arr[i] - min]++;
        }
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i-1];
        }
        for (int i = arr.length - 1; i >= 0; i--) {
            output[count[arr[i] - min] - 1] = arr[i];
            count[arr[i] - min]--;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = output[i];
        }
        */

        /*
        Old Code that used Merge Sort
        int left = 0;
        int right = arr.length - 1;
        helperSort(arr, left, right); 
        */
        }

    /* Helper Method for merge sort that recursively calls itself and merge sort until array is sorted */
    void helperSort(int[] arr, int left, int right) {
        if (left < right) {
            int middle = (left + right) /2;
            helperSort(arr, left, middle);
            helperSort(arr, middle + 1, right);
            merge(arr, left, middle, right);
        }
    }

    /* Merge Sort Method */
    void merge(int[] arr, int left, int middle, int right) {
        /* Finds the two sizes of the two subarrays to be merged */
        int length1 = middle - left + 1;
        int length2 = right - middle;

        /* Temporary arrays using the sizes foudn above */
        int[] leftTemp = new int [length1];
        int[] rightTemp = new int [length2];

        /* Copies arr data into temporary arrays */ 
        for (int i = 0; i < length1; i++) {
            leftTemp[i] = arr[left + i];
        }
        for (int i = 0; i < length2; i++) {
            rightTemp[i] = arr[middle + 1 + i];
        }

        /* Initializes indexes of array beforehand, very slightly enhances performance */
        int i = 0;
        int j = 0;
        /* Initializes index of the to be merged sort */
        int k = left;
        while (i < length1 && j < length2) {
            if (leftTemp[i] <= rightTemp[j]) {
                arr[k] = leftTemp[i];
                i++;
            } else {
                arr[k] = rightTemp[j];
                j++;
            }
            k++;
        }
        /* Copies the remaining elements from the left sub array */
        while (i < length1) {
            arr[k] = leftTemp[i];
            i++;
            k++;
        }
        /* Copies the remaining elements from the right sub array */
        while(j < length2) {
            arr[k] = rightTemp[j];
            j++;
            k++;
        }
    }

    /* Helper method that iterates through to find the maximum element */ 
    int getMax(int[] arr) {
        int n = arr.length;
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    /* Helper method that iterates through to find the minimum element
       Older Code thats no longer used */
    int getMin(int[] arr) {
        int n = arr.length;
        int min = arr[0];
        for (int i = 0; i < n; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }

    /* Checker method that checks if the array is already sorted, saves time */
    boolean checkIfSorted(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length-1; i++) {
            if (arr[i] > arr[i+1]) {
                return false;
            }
        }
        return true;
    }

    /* Radix Sort which performs which performs counting sort based off of the digit that exp current represents */
    void countSort(int arr[], int n, int exp) {
        /* intializes i beforehand to slighly enhance performance */
        int i;
        /* initializes count array and output array */
        int output[] = new int[n];
        int count[] = new int[10];
        /* Stores the count of each value in the count array array*/
        for (i = 0; i < n; i++) {
            count[(arr[i]/exp)%10]++;
        }
        /* Increments count so that the count array now conatins the actual position of each digit in the output array */
        for (i = 1; i < 10; i++) {
            count[i] += count[i-1];
        }
        /* Constructs the output array */
        for (i = n -1; i >= 0; i--) {
            output[count[(arr[i]/exp)%10] - 1] = arr[i];
            count[(arr[i]/exp)%10]--;
        }
        /* Copies the output array to the original data array in sorted order */
        for (i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }

    /* Returns author */
    public String getAuthor() {
    	/* You may change the following line of code*/
        return "bts222";
    }

}
