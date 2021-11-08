package lvc.cds;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Sorting {

    private static final Random rand = new Random();

    // book's mergeSort
    public static <T> void mergeSort(T[] data, Comparator<T> comp) {
        if (data.length <= 1) {
            return;
        } 
        T[] left = Arrays.copyOfRange(data, 0, data.length/2);
        T[] right = Arrays.copyOfRange(data, data.length/2, data.length);
        mergeSort(left, comp);
        mergeSort(right, comp);
        merge(left, right, data, comp);
    }

    // book's merge method for mergeSort
    public static <T> void merge(T[] left, T[] right, T[] data, Comparator<T> comp) {
        int leftidx = 0, rightidx = 0;
        for (int i = 0; i < data.length; i++) {
            if (leftidx == left.length) {
                data[i] = right[rightidx++];
            } else if (rightidx == right.length) {
                data[i] = left[leftidx++];
            } else if (comp.compare(left[leftidx], right[rightidx]) < 0) {
                data[i] = left[leftidx++];
            } else  {
                data[i] = right[rightidx++];
            }
        }
    }


    // public mergeSort to get the array passed to the new version
    public static <T> void mergeSort2(T[] data, Comparator<T> comp) {
        T[] temp = data.clone();
        mergeSort2(data, temp, 0, data.length, comp);
    }


    // new version of mergeSort
    private static <T> void mergeSort2(T[] data, T[] temp, int left, int right, Comparator<T> comp) {
        if (right - left <= 1) {
            return;
        }
        // recursively call for the left and right halves
        int midpoint = left + (right-left)/2;
        mergeSort2(data, temp, left, midpoint, comp);
        mergeSort2(data, temp, midpoint, right, comp);
        merge2(data, temp, left, midpoint, midpoint, right, comp);
    }


    // between left1 and right1 are the items in the first part
    // between left2 nad right2 are the items in the second part
    private static <T> void merge2(T[] data, T[] temp, int left1, int right1, int left2, int right2, Comparator<T> comp) {
        int start = left1;
        for (int i = left1; i < right2; i++) {
            if (left1 == right1) {
                temp[i] = data[left2++];
            } else if (left2 == right2) {
                temp[i] = data[left1++];
            } else if (comp.compare(data[left1], data[left2]) < 0) {
                temp[i] = data[left1++];
            } else {
                temp[i] = data[left2++];
            }
        }

        // temp is sorted for these indeces, put them back into data
        for (int i = start; i < right2; i++) {
            data[i] = temp[i];
        }
        
    }


    // books quickSort
    public static <T> void quickSort(T[] data, Comparator<T> comp) {
        quickSort(data, 0, data.length, comp);
    }

    // books quickSort
    private static <T> void quickSort(T[] data, int start, int end, Comparator<T> comp) {
        if (end <= 1)  {
            return;
        }
        T pivot = data[start + rand.nextInt(end)];
        int p = start-1, j = start, q = start+end;
        // a[i..p]<x, a[p+1..q-1]??x, a[q..i+n-1]>x
        while (j < q) {
            int c = comp.compare(data[j], pivot);
            if (c < 0) { // move to beginning of array
                swap(data, j++, ++p);
            } else if (c > 0) {
                swap(data, j, --q); // move to end of array
            } else {
                j++; // keep in the middle
            }
        }
        // a[i..p]<x, a[p+1..q-1]=x, a[q..i+n-1]>x
        quickSort(data, start, p-start+1, comp);
        quickSort(data, q, end-(q-start), comp);
    }

    // swaps two items in an array
    private static <T> void swap(T[] data, int first, int second) {
        T temp = data[first];
        data[first] = data[second];
        data[second] = temp;
    }


    // new public version of quickSort
    public static <T> void quickSort2(T[] data, Comparator<T> comp) {
        quickSort2(data, 0, data.length, comp);
    }


    // new version of quicksort
    private static <T> void quickSort2(T[] data, int start, int end, Comparator<T> comp) {
        int cutoff = 10;
        if (end - start < cutoff) {
            // size of array in question is smaller than cutoff, call selectionSort
            insertionSort(data, start, end, comp);
            return;
        } else {
        // median of three partiotioning
        int pivotIdx = partition(data, start, end, comp);
        // val is the index of the pivot, and therefore in the correct spot
        quickSort2(data, start, pivotIdx, comp);
        quickSort2(data, pivotIdx+1, end, comp);
        }
    }


    // everything to the left of the pivot should end being smaller, everything to the right bigger
    private static <T> int partition(T[] data, int left, int right, Comparator<T> comp) {
        // median of three
        medOfThree(data, left, (left + (right - left)/2), right, comp);
        T pivot = data[right-2];
        int pivotLoc = right-2;

        // start the right idx at the item to the left of the pivot
        right = right - 3;
        while (left <= right) {
            int c = comp.compare(data[left], pivot);
            if (c > 0) {
                int c2 = comp.compare(data[right], pivot);
                if (c2 < 0) {
                    swap(data, left, right);
                    left++;
                    right--;
                }else {
                    right--;
                }
            } else {
                left++;
            }
        }
        // left has passed the right, swap the left pointer with the pivot
        // return the location where the pivot swapped to
        swap(data, left, pivotLoc);
        return left;
    }


    // median of three partitioning
    private static <T> void medOfThree(T[] data, int left, int mid, int right, Comparator<T> comp) {
        // put the left, mid, and right items in order
        if (comp.compare(data[left], data[mid]) > 0) {
            swap(data, left, mid);
        }
        if (comp.compare(data[mid], data[right-1]) > 0) {
            swap(data, mid, right-1);
        }
        if (comp.compare(data[left], data[mid]) > 0) {
            swap(data, left, mid);
        }
        // make the middle item the pivot and move it to the right - 2
        swap(data, mid, right-2);
    }


    // sorting for smaller arrays in O(n^2) time
    private static <T> void insertionSort(T[] data, int start, int end, Comparator<T> comp) {   
        for (int j = start+1; j < end; j++) {  
            T key = data[j];  
            int i = j-1;  
            while ( (i > -1) && ( comp.compare(data[i], key) > 0)) {  
                data[i+1] = data[i];  
                i--;  
            }  
            data[i+1] = key;  
        }  
    } 
}
