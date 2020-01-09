
package com.sevanetrebchenko;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SortingAlgorithms {

    public static void main(String[] args) {
        File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\random10000.txt");
        Algorithms algorithms = new Algorithms(file);
        algorithms.run();
    }

}
