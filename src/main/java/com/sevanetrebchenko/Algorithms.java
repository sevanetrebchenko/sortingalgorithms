package com.sevanetrebchenko;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
        long startingSortTime = System.currentTimeMillis();
        selectionSort();
        long endingSelectionSortTime = System.currentTimeMillis();
        System.out.println("Time taken for selection sort: " + (endingSelectionSortTime - startingSortTime) + " milliseconds.");
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
//
//    public static void bubbleSort(ArrayList<Integer> number) {
//        for (int i = 0; i < number.size()-1; i++) {
//            int j = i + 1;
//            if (number.get(i) > number.get(j)) {
//                int temporary = number.get(i);
//                number.set(i, number.get(j));
//                number.set(j, temporary);
//            }
//        }
//    }

    public ArrayList<Integer> selectionSort() {
        int sortPosition = 0;
        int smallestPosition;

        for (int i = 0; i < list.size() - 1; i++) {
            smallestPosition = i;
            int smallestValue = list.get(smallestPosition);

            for (int j = 1 + i; j < list.size(); j++) {
                int currentValue = list.get(j);
                if (currentValue < smallestValue) {
                    smallestValue = currentValue;
                    smallestPosition = j;
                }
            }

            // we have the smallest value, swap with the next value in the array
            swapElements(list, smallestPosition, sortPosition);
            sortPosition++;
        }
        return list;
    }

    private <T> void swapElements(ArrayList<T> list, int firstPosition, int secondPosition) {
        T tempValue = list.get(firstPosition);
        list.set(firstPosition, list.get(secondPosition));
        list.set(secondPosition, tempValue);
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
