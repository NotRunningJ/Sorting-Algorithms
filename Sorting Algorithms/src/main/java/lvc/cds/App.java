package lvc.cds;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public final class App extends Sorting {

    private static final Random rand = new Random();
    private static final Comparator<Integer> intComp = new DefaultComparator<>();
    private static final Comparator<String> stringComp = new DefaultComparator<>();
    private static final int cap = 1_000_000;
    private static final double CONVERT = 1_000_000.0;
    private static final Character[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                                                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 
                                                'v', 'w', 'x', 'y', 'v'};

    // tests whether or not an array is sorted
    public static <T> boolean isSorted(T[] data, Comparator<T> comp) {
        for (int i = 0; i < data.length-1; i++) {
            if (comp.compare(data[i], data[i+1]) > 0) {
                return false;
            }
        }
        return true;
    }

    // tests if mergeSort actually sorts
    public static void testMerge() {
        System.out.println();
        Integer[] test = new Integer[100];
        for (int i = 0; i < 100; i++) {
            test[i] = rand.nextInt(10);
        }
        System.out.println("sorted before mergeSort? " + isSorted(test, intComp));
        mergeSort(test, intComp);
        System.out.println("sorted after mergeSort? " + isSorted(test, intComp));
    }

    // tests if mergeSort2 actually sorts
    public static void testMerge2() {
        System.out.println();
        Integer[] test = new Integer[100];
        for (int i = 0; i < 100; i++) {
            test[i] = rand.nextInt(10);
        }
        System.out.println("sorted before mergeSort2? " + isSorted(test, intComp));
        mergeSort2(test, intComp);
        System.out.println("sorted after mergeSort2? " + isSorted(test, intComp));
    }

    // tests if quickSort actually sorts
    public static void testQuick() {
        System.out.println();
        Integer[] test = new Integer[100];
        for (int i = 0; i < 100; i++) {
            test[i] = rand.nextInt(10);
        }
        System.out.println("sorted before quickSort? " + isSorted(test, intComp));
        quickSort(test, intComp);
        System.out.println("sorted after quickSort? " + isSorted(test, intComp));
    }

    // tests if quickSort2 actually sorts
    public static void testQuick2() {
        System.out.println();
        Integer[] test = new Integer[100];
        for (int i = 0; i < 100; i++) {
            test[i] = rand.nextInt(10);
        }
        System.out.println("sorted before quickSort2? " + isSorted(test, intComp));
        quickSort2(test, intComp);
        System.out.println("sorted after quickSort2? " + isSorted(test, intComp));
    }


    // times how long it takes to sort using merge
    public static <T> double timeMerge(T[] data, Comparator<T> comp) {
        long start = System.nanoTime();
        mergeSort(data, comp);
        long elapsed = System.nanoTime() - start;
        double time = elapsed/CONVERT;
        return time;
    }

    // times how long it takes to sort using merge2
    public static <T> double timeMerge2(T[] data, Comparator<T> comp) {
        long start = System.nanoTime();
        mergeSort2(data, comp);
        long elapsed = System.nanoTime() - start;
        double time = elapsed/CONVERT;
        return time;
    }

    // times how long it takes to sort using quickSort
    public static <T> double timeQuick(T[] data, Comparator<T> comp) {
        long start = System.nanoTime();
        quickSort(data, comp);
        long elapsed = System.nanoTime() - start;
        double time = elapsed/CONVERT;
        return time;
    }

    // times how long it takes to sort using quicSort2
    public static <T> double timeQuick2(T[] data, Comparator<T> comp) {
        long start = System.nanoTime();
        quickSort2(data, comp);
        long elapsed = System.nanoTime() - start;
        double time = elapsed/CONVERT;
        return time;
    }

    // times how long it takes to sort using the default sorting method
    public static <T> double timeDefault(T[] data) {
        long start = System.nanoTime();
        Arrays.sort(data);
        long elapsed = System.nanoTime() - start;
        double time = elapsed/CONVERT;
        return time;
    }


    // generates an array of random strings where the first 10 characters are the same
    public static String[] genRandomStrings(int size) {
        String[] temp = new String[size];
        for (int i = 0; i < size; i++) {
            StringBuilder sb = new StringBuilder();
            int strSize = rand.nextInt(10) + 20;
            for (int k = 0; k < 10; k++) {
                sb.append(alphabet[3]);
            }
            for (int j = 0; j < strSize - 10; j++) {
                int r = rand.nextInt(alphabet.length);
                sb.append(alphabet[r]);
            }
            temp[i] = sb.toString();
        }
        return temp;
    }


    // generates an array of sorted strings
    public static String[] genSortedStrings(int size) {
        String[] temp = genRandomStrings(size);
        Arrays.sort(temp);
        return temp;
    }


    // generates an array of reverse sorted strings
    public static String[] genReverseStrings(int size) {
        String[] temp = genRandomStrings(size);
        Arrays.sort(temp, Collections.reverseOrder());
        return temp;
    }


    // creates a table of the 4 different sorts for random entries
    public static void testRandom() {
        // print tests in merge, merge2, quick, quick2 order as titles for the table
        System.out.println();
        System.out.println("Table for random integers: ");
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s\n", "size", "merge", "merge2", "quick",
                                                                 "quick2", "default");

        for (int size = 100; size <= cap; size *= 10) {
            // build the array & clone it 
            Integer[] temp = new Integer[size];
            for (int i = 0; i < size; i++) {
                temp[i] = i;
            }
            Integer[] temp2 = temp.clone();
            Integer[] temp3 = temp.clone();
            Integer[] temp4 = temp.clone();
            Integer[] temp5 = temp.clone();

            // times for the sortings
            double mergeTime = timeMerge(temp, intComp);
            double merge2Time = timeMerge2(temp2, intComp);
            double quickTime = timeQuick(temp3, intComp);
            double quick2Time = timeQuick2(temp4, intComp);
            double defaultTime = timeDefault(temp5);

            // make a table from the sizes and times
            System.out.printf("%-15d %-15f %-15f %-15f %-15f %-15f\n", size, mergeTime, merge2Time,
                                                            quickTime, quick2Time, defaultTime);
        }
    }


    // creates a table of the 4 different sorts for ordered arrays
    public static void testOrdered() {
        // print tests in merge, merge2, quick, quick2 order as titles for the table
        System.out.println();
        System.out.println("Table for already sorted integers: ");
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s\n", "size", "merge", "merge2", "quick",
                                                                    "quick2", "default");


        for (int size = 100; size <= cap; size *= 10) {
            // build the array & clone it 
            Integer[] temp = new Integer[size];
            for (int i = 0; i < size; i++) {
                temp[i] = i;
            }
            Integer[] temp2 = temp.clone();
            Integer[] temp3 = temp.clone();
            Integer[] temp4 = temp.clone();
            Integer[] temp5 = temp.clone();

            // times for the sortings
            double mergeTime = timeMerge(temp, intComp);
            double merge2Time = timeMerge2(temp2, intComp);
            double quickTime = timeQuick(temp3, intComp);
            double quick2Time = timeQuick2(temp4, intComp);
            double defaultTime = timeDefault(temp5);

            // make a table from the sizes and times
            System.out.printf("%-15d %-15f %-15f %-15f %-15f %-15f\n", size, mergeTime, merge2Time,
                                                                quickTime, quick2Time, defaultTime);
        }
    }


    // creates a table of the 4 different sorts for reverse sorted arrays
    public static void testReverse() {
        // print tests in merge, merge2, quick, quick2 order as titles for the table
        System.out.println();
        System.out.println("Table for reverse sorted integers: ");
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s\n", "size", "merge", "merge2", "quick",
                                                                    "quick2", "default");

        for (int size = 100; size <= cap; size *= 10) {
            // build the array & clone it 
            Integer[] temp = new Integer[size];
            for (int i = 0; i < size; i++) {
                temp[i] = i;
            }
            Integer[] temp2 = temp.clone();
            Integer[] temp3 = temp.clone();
            Integer[] temp4 = temp.clone();
            Integer[] temp5 = temp.clone();

            // times for the sortings
            double mergeTime = timeMerge(temp, intComp);
            double merge2Time = timeMerge2(temp2, intComp);
            double quickTime = timeQuick(temp3, intComp);
            double quick2Time = timeQuick2(temp4, intComp);
            double defaultTime = timeDefault(temp5);

            // make a table from the sizes and times
            System.out.printf("%-15d %-15f %-15f %-15f %-15f %-15f\n", size, mergeTime, merge2Time,
                                                                quickTime, quick2Time, defaultTime);
        }
    }


    public static <T> void testRandomStrings() {
        // print tests in merge, merge2, quick, quick2 order as titles for the table
        System.out.println();
        System.out.println("Table for random Strings ");
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s\n", "size", "merge", "merge2", "quick",
                                                                "quick2", "default");
        
        for (int size = 100; size <= cap; size *= 10) {
            String[] temp = genRandomStrings(size);
            String[] temp2 = temp.clone();
            String[] temp3 = temp.clone();
            String[] temp4 = temp.clone();
            String[] temp5 = temp.clone();

            // times for the sortings
            double mergeTime = timeMerge(temp, stringComp);
            double merge2Time = timeMerge2(temp2, stringComp);
            double quickTime = timeQuick(temp3, stringComp);
            double quick2Time = timeQuick2(temp4, stringComp);
            double defaultTime = timeDefault(temp5);

            // make a table from the sizes and times
            System.out.printf("%-15d %-15f %-15f %-15f %-15f %-15f\n", size, mergeTime, merge2Time,
                                                                quickTime, quick2Time, defaultTime);
        }
    }

    public static <T> void testSortedStrings() {
        // print tests in merge, merge2, quick, quick2 order as titles for the table
        System.out.println();
        System.out.println("Table for sorted Strings ");
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s\n", "size", "merge", "merge2", "quick",
                                                                "quick2", "default");
        
        for (int size = 100; size <= cap; size *= 10) {
            String[] temp = genSortedStrings(size);
            String[] temp2 = temp.clone();
            String[] temp3 = temp.clone();
            String[] temp4 = temp.clone();
            String[] temp5 = temp.clone();

            // times for the sortings
            double mergeTime = timeMerge(temp, stringComp);
            double merge2Time = timeMerge2(temp2, stringComp);
            double quickTime = timeQuick(temp3, stringComp);
            double quick2Time = timeQuick2(temp4, stringComp);
            double defaultTime = timeDefault(temp5);

            // make a table from the sizes and times
            System.out.printf("%-15d %-15f %-15f %-15f %-15f %-15f\n", size, mergeTime, merge2Time,
                                                                quickTime, quick2Time, defaultTime);
        }
    }


    public static <T> void testReverseStrings() {
        // print tests in merge, merge2, quick, quick2 order as titles for the table
        System.out.println();
        System.out.println("Table for reverse sorted Strings ");
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s\n", "size", "merge", "merge2", "quick",
                                                                "quick2", "default");
        
        for (int size = 100; size <= cap; size *= 10) {
            String[] temp = genReverseStrings(size);
            String[] temp2 = temp.clone();
            String[] temp3 = temp.clone();
            String[] temp4 = temp.clone();
            String[] temp5 = temp.clone();

            // times for the sortings
            double mergeTime = timeMerge(temp, stringComp);
            double merge2Time = timeMerge2(temp2, stringComp);
            double quickTime = timeQuick(temp3, stringComp);
            double quick2Time = timeQuick2(temp4, stringComp);
            double defaultTime = timeDefault(temp5);

            // make a table from the sizes and times
            System.out.printf("%-15d %-15f %-15f %-15f %-15f %-15f\n", size, mergeTime, merge2Time,
                                                                quickTime, quick2Time, defaultTime);
        }
    }




    public static void main(String[] args) {

        // check to make sure they are actually sorted
        testMerge();
        testMerge2();
        testQuick();
        testQuick2();

        // compare runtimes of the different sorting algorithms
        testOrdered();
        testRandom();
        testReverse();
        testRandomStrings();
        testSortedStrings();
        testReverseStrings();

        /**
         * ANALYSIS
         * It makes sense for all the algorithms to take longer when an already sorted array
         * is passed in because it has to check every single element then. It follows out assumptions
         * that quickSort2 is faster than both quickSort and mergeSort.
         * 
         * The reason the default sorting mehtod is superior to quickSort is becuase 
         * it used 2 pivots instead of one, partitioning the data into 3 parts instead of 2, which is much 
         * faster. Plugging the timing tests into excel shows me that all the algorithms we created are 
         * running on thier O(nlog(n)) time complexity.
         */
    }
}
