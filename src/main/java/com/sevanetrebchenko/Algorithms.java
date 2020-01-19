package com.sevanetrebchenko;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Algorithms extends Thread {
    private ArrayList<Integer> list;
    private File file;
    private final Object lock;
    private boolean shuffled = false;
    private boolean multithreaded;
    private volatile boolean threadExit = false;


    Algorithms(File file, Pair<Object, Boolean> threadTools) {
        this.file = file;
        this.lock = threadTools.getFirst();
        this.multithreaded = threadTools.getSecond();
        list = new ArrayList<Integer>();
        initialize();
    }

    private void initialize() {
        // Read in the data
        long readStartTime = System.currentTimeMillis();
        readData();
        long readEndTime = System.currentTimeMillis();

        // Time to add all the numbers to the ArrayList
        System.out.println("Time to read file: " + (readEndTime - readStartTime) + " milliseconds. List contains " + this.list.size() + " elements.");
    }

    public void run() {
        long startingSortTime;
        long endingSortTime;

        // selection sort
        System.out.println("Shuffling...");
        shuffleData();

        startingSortTime = System.currentTimeMillis();
//        selectionSort();
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

//        startingSortTime = System.currentTimeMillis();
//        recursiveBubbleSort(list.size());
//        endingSortTime = System.currentTimeMillis();
//        System.out.println("Time taken for recursive bubble sort: " + (endingSortTime - startingSortTime) + " milliseconds.");
//
//        // insertion sort
//        System.out.println("Shuffling...");
//        shuffleData();

        startingSortTime = System.currentTimeMillis();
        insertionSort();
        endingSortTime = System.currentTimeMillis();
        System.out.println("Time taken for insertion sort: " + (endingSortTime - startingSortTime) + " milliseconds.");
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
//    public static void mergeSort(int [] toSort) {
//        int [] left = Arrays.copyOfRange(toSort, 0, toSort.length/2);
//        int [] right = Arrays.copyOfRange(toSort, toSort.length/2, toSort.length);
//
//        if (toSort.length <= 2) {
//
//        }
//    }

    public void mergeSort() {
    }

    public void insertionSort() {
        this.shuffled = false;
        System.out.println("starting insertion sort");

        for (int i = 1; i < this.list.size(); ++i) {
            if (!threadExit) {
                // get number to insert
                int valueToInsert = this.list.get(i);

                // move everything over one
                // index to backtrack and insert properly
                int index = i;

                // backtrack
                while (index > 0 && valueToInsert < this.list.get(index - 1)) {
                    this.list.set(index, this.list.get(index - 1));
                    --index;
                }

                this.list.set(index, valueToInsert);
                if (this.multithreaded) {
                    this.synchronize();
                }
            }
            else {
                System.out.println("Thread exiting prematurely");
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("finished insertion sort");
    }

    public void recursiveBubbleSort(int listSize) {
        this.shuffled = false;

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
        this.shuffled = false;
        boolean finished = false;

        for (int i = 0; i < this.list.size() - 1 && !finished; ++i) {
            for (int j = 0; j < this.list.size() - i - 1; ++j) {
                if (!this.threadExit) {
                    if (this.list.get(j) > this.list.get(j + 1)) {
                        swapElements(j, j + 1);
                        if (this.multithreaded) {
                            this.synchronize();
                        }
                    }
                }
                else {
                    finished = true;
                    System.out.println("Thread exiting prematurely");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public void selectionSort() {
        this.shuffled = false;
        int sortPosition = 0;
        int smallestPosition;

        for (int i = 0; i < this.list.size() - 1; ++i) {
            if (!this.threadExit) {

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
                if (this.multithreaded) {
                    this.synchronize();
                }
            }
            else {
                System.out.println("Thread exiting prematurely");
                Thread.currentThread().interrupt();
                break;
            }
        }

        if (this.isSorted()) {
        }
    }

    public int[] getData() {
        int [] data = new int [this.list.size()];
        for (int i = 0 ; i < this.list.size(); ++i) {
            data[i] = this.list.get(i);
        }

        return data;
    }

    public void shuffleData() {
        Random random = new Random();
        for (int i = list.size() - 1; i > 0; --i) {
            if(!this.threadExit) {
                int j = random.nextInt(i + 1);
                swapElements(i, j);
                if (this.multithreaded) {
                    this.synchronize();
                }
            }
            else {
                System.out.println("Thread exiting prematurely");
                Thread.currentThread().interrupt();
                break;
            }
        }

        this.shuffled = true;
    }

    public boolean isSorted() {
        for (int i = 0; i < this.list.size() - 1; i++) {
            if(this.list.get(i).compareTo(this.list.get(i + 1)) > 0){
                return false;
            }
        }
        return true;
    }

    public boolean isShuffled() {
        return this.shuffled;
    }

    public void shuffle() {
        this.shuffled = false;
    }

    public void stopThread() {
        this.threadExit = true;
    }

    private void synchronize() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                if (!this.isSorted()) {
                    System.err.println(Thread.currentThread().getName() + " INTERRUPTED");
                }
                else {
                    System.out.println("Sorting finished.");
                }
            }
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
