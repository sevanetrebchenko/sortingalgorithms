package com.sevanetrebchenko;

public class AlgorithmsThread extends Thread {
    public enum SortingAlgorithm {
        SELECTION,
        BUBBLE,
        INSERTION,
        SHUFFLE,
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
            case SHUFFLE:
                data.shuffleData();
                break;
        }
    }

    public String getAlgorithm() {
        switch (this.sortingAlgorithm) {
            case SELECTION:
                return "SELECTION SORT";
            case BUBBLE:
                return "BUBBLE SORT";
            case INSERTION:
                return "INSERTION SORT";
            case SHUFFLE:
                return "SHUFFLE";
            default:
                return "INVALID";
        }
    }

    public boolean isSorted() {
        return this.data.isSorted();
    }

    public boolean isShuffled() {
        return this.data.isShuffled();
    }

    public void shuffle() {
        this.data.shuffle();
    }

    public SortingAlgorithm getType() {
        return this.sortingAlgorithm;
    }

    public void stopThread() {
        this.data.stopThread();
    }
}
