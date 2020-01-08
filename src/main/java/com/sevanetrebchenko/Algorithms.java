package com.sevanetrebchenko;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Algorithms {
    private ArrayList<Integer> list;
    private File file;

    Algorithms(File file) {
        this.file = file;
        list = new ArrayList<Integer>();
    }

    public void initialize() {
        // Read in the data
        long readStartTime = System.currentTimeMillis();
        readData();
        long readEndTime = System.currentTimeMillis();

        // Time to add all the numbers to the ArrayList
        System.out.println("Time to read file: " + (readEndTime - readStartTime) + " milliseconds.");

    }

    public void run() {
        // selection sort
        long startingSortTime;
        long endingSortTime;

        // selection sort
        System.out.println("Shuffling...");
        shuffleData();

        startingSortTime = System.currentTimeMillis();
        selectionSort();
        endingSortTime = System.currentTimeMillis();
        System.out.println("Time taken for selection sort: " + (endingSortTime - startingSortTime) + " milliseconds.");

        // bubble sort
        System.out.println("Shuffling...");
        shuffleData();

        startingSortTime = System.currentTimeMillis();
        bubbleSort();
        endingSortTime = System.currentTimeMillis();
        System.out.println("Time taken for bubble sort: " + (endingSortTime - startingSortTime) + " milliseconds.");

        // recursive bubble sort
        System.out.println("Shuffling...");
        shuffleData();

        startingSortTime = System.currentTimeMillis();
        recursiveBubbleSort(list.size());
        endingSortTime = System.currentTimeMillis();
        System.out.println("Time taken for recursive bubble sort: " + (endingSortTime - startingSortTime) + " milliseconds.");

        // merge sort

    }

//    public static ArrayList radixSort(int maximum, ArrayList <Integer> original, ArrayList <Integer> sorted, int module) {
//        // sort by the ones place
//        for (int i = 0; i < original.size(); i++) {
//            sorted.add(original.get(i) % module);
//        }
//        module *= 10;
//        if (module == maximum) {
//            return sorted;
//        }
//        else {
//            return radixSort(maximum, original, sorted, module);
//        }
//    }
//
//    public static int getMaximum (ArrayList <Integer> toSort) {
//        int maximum = toSort.get(0);
//        for (Integer integer : toSort) {
//            if (integer > maximum) {
//                maximum = integer;
//            }
//        }
//        return maximum;
//    }
//
//    public static void mergeSort(int [] toSort) {
//        int [] left = Arrays.copyOfRange(toSort, 0, toSort.length/2);
//        int [] right = Arrays.copyOfRange(toSort, toSort.length/2, toSort.length);
//
//        if (toSort.length <= 2) {
//
//        }
//    }

    public void recursiveBubbleSort(int listSize) {
        // base case
        if (listSize == 1) {
            return;
        }

        for (int i = 0; i < listSize - 1; ++i) {
            if (list.get(i) > list.get(i + 1)) {
                swapElements(i, i + 1);
            }
        }

        recursiveBubbleSort(--listSize);
    }

    public void bubbleSort() {
        for (int i = 0; i < this.list.size() - 1; ++i) {
            for (int j = 0; j < this.list.size() - i - 1; ++j) {
                if (this.list.get(j) > this.list.get(j + 1)) {
                    swapElements(j, j + 1);
                }
            }
        }
    }

    public void selectionSort() {
        int sortPosition = 0;
        int smallestPosition;

        for (int i = 0; i < this.list.size() - 1; ++i) {
            smallestPosition = i;
            int smallestValue = this.list.get(smallestPosition);

            for (int j = 1 + i; j < this.list.size(); ++j) {
                int currentValue = this.list.get(j);
                if (currentValue < smallestValue) {
                    smallestValue = currentValue;
                    smallestPosition = j;
                }
            }

            // we have the smallest value, swap with the next value in the array
            swapElements(smallestPosition, sortPosition);
            sortPosition++;
        }
    }

    private void shuffleData() {
        Random random = new Random();
        for (int i = list.size() - 1; i > 0; --i) {
            int j = random.nextInt(i + 1);
            swapElements(i , j);
        }
    }

    private void swapElements(int firstPosition, int secondPosition) {
        int tempValue = this.list.get(firstPosition);
        this.list.set(firstPosition, this.list.get(secondPosition));
        this.list.set(secondPosition, tempValue);
    }

    private void readData() {
        Scanner reader = null;
        try {
            reader = new Scanner(this.file);
        }
        catch (FileNotFoundException e) {
            System.out.println("Unable to locate file in the specified directory.");
            return;
        }

        // read file into arraylist
        while (reader.hasNextLine()) {
            list.add(Integer.parseInt(reader.nextLine()));
        }
    }
}
