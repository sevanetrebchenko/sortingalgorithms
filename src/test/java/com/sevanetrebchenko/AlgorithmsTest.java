package com.sevanetrebchenko;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class AlgorithmsTest {

    private static Algorithms test;

    private void check(int [] data) {
        int [] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertArrayEquals(data, expected);
    }

    @BeforeAll
    static void initialize() {
        test = new Algorithms(new File(System.getProperty("user.dir") + "\\src\\test\\resources\\random10.txt"));
    }

    @Test
    void recursiveBubbleSort() {
        int LIST_SIZE = 10;
        test.recursiveBubbleSort(LIST_SIZE);
        check(test.getData());
    }

    @Test
    void bubbleSort() {
        test.bubbleSort();
        check(test.getData());
    }

    @Test
    void selectionSort() {
        test.selectionSort();
        check(test.getData());
    }
}