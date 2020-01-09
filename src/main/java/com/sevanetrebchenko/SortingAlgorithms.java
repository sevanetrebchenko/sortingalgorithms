
package com.sevanetrebchenko;

import java.awt.*;
import java.io.File;


public class SortingAlgorithms {

    public static void main(String[] args) {
        File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\random10000.txt");
        Algorithms algorithms = new Algorithms(file);
        algorithms.run();

        AlgorithmsGraphics graphics = new AlgorithmsGraphics(new Dimension(600, 800));
        graphics.run();
    }

}
