package com.sevanetrebchenko;

import org.junit.jupiter.api.Test;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class AlgorithmsTest {

    private final int LIST_SIZE = 10;

    private void check(int [] data) {
        int [] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertArrayEquals(data, expected);
    }

    @Test
    void recursiveBubbleSort() {
        Algorithms test = new Algorithms(new File(System.getProperty("user.dir") + "\\src\\test\\resources\\random10.txt"));
        test.recursiveBubbleSort(LIST_SIZE);
        check(test.getData());
    }

    @Test
    void bubbleSort() {
        Algorithms test = new Algorithms(new File(System.getProperty("user.dir") + "\\src\\test\\resources\\random10.txt"));
        test.bubbleSort();
        check(test.getData());
    }

    @Test
    void selectionSort() {
        Algorithms test = new Algorithms(new File(System.getProperty("user.dir") + "\\src\\test\\resources\\random10.txt"));
        test.selectionSort();
        check(test.getData());
    }
}