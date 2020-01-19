package com.sevanetrebchenko;

public class AlgorithmsThread extends Thread {
    public enum SortingAlgorithm {
        SELECTION,
        BUBBLE,
        INSERTION,
    }

    private SortingAlgorithm sortingAlgorithm;
    private Algorithms data;

    public AlgorithmsThread(Algorithms data, SortingAlgorithm sortType) {
        this.sortingAlgorithm = sortType;
        this.data = data;
    }

    public void run() {
        switch (this.sortingAlgorithm) {
            case SELECTION:
                data.selectionSort();
                break;
            case BUBBLE:
                data.bubbleSort();
                break;
            case INSERTION:
                data.insertionSort();
                break;
        }
    }

    public boolean isSorted() {
        return this.data.isSorted();
    }

    public int[] getData() {
        return this.data.getData();
    }
}
