package com.sevanetrebchenko;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import java.awt.*;
import java.io.File;

import static org.lwjgl.opengl.GL11.*;

public class AlgorithmsGraphics extends Thread {
    private Dimension screenSize;
    private Algorithms algorithms;
    private boolean threadStarted = false;
    private AlgorithmsThread current;
    private final Object lock = new Object();


    AlgorithmsGraphics(Dimension dim) {
        this.screenSize = dim;
    }

    public void init() {
        // initialize display
        try {
            Display.setTitle("Visualizing Sorting Algorithms");
            Display.setDisplayMode(new DisplayMode(this.screenSize.width, this.screenSize.height));
            Display.create();
            glViewport(0, 0, Display.getWidth(), Display.getHeight());
            glLoadIdentity();
            glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
        }

        catch (LWJGLException e) {
            System.out.println(e.toString());
        }

        File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\random100.txt");
        this.algorithms = new Algorithms(file, this.lock);
    }

    public void run() {
        // While the Display is still active on the screen.
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            glClear(GL_COLOR_BUFFER_BIT);
            glColor3f(255, 255, 255);


            if (Keyboard.isKeyDown(Keyboard.KEY_1) && !threadStarted) {
                this.current = new AlgorithmsThread(this.algorithms, AlgorithmsThread.SortingAlgorithm.INSERTION);
                current.setName("SelectionSort Thread");
                current.start();
                this.threadStarted = true;
            }

            if (this.threadStarted) {
                if (!current.isSorted()) {
                    synchronized (lock) {
                        System.out.println("updating");
                        printData(current.getData());
                        update(current.getData());
                        lock.notify();
                    }
                }
                else {
                    update(current.getData());
                }
            }

            Display.update();
            Display.sync(60);
        }
    }

    private void printData(int[] data) {
        System.out.println("Data: ");
        for (int point : data) {
            System.out.print(point + ", ");
        }
        System.out.println();
        System.out.println();
    }

    private void update(int [] data) {
        int rectangleWidth = Math.round(Display.getWidth() / data.length);
        int offset = 0;
        for (int point : data) {
            int rectangleHeight = Math.round(mapValueToRange(point, 0, getMaxValue(data), 0, Display.getHeight()));
            glBegin(GL_QUADS);
                glVertex2i(offset, 0);
                glVertex2i(offset + rectangleWidth, 0);
                glVertex2i(offset + rectangleWidth, rectangleHeight);
                glVertex2i(offset, rectangleHeight);
            glEnd();
            offset += rectangleWidth;
        }
    }

    private int getMaxValue(int[] data) {
        int maximum = Integer.MIN_VALUE;
        for (int i = 0; i < data.length; ++i) {
            if (data[i] > maximum) {
                maximum = data[i];
            }
        }

        return maximum;
    }

    private float mapValueToRange(float value, float inputRangeMin, float inputRangeMax, float outputRangeMin, float outputRangeMax) {
        return (value - inputRangeMin) * (outputRangeMax - outputRangeMin) / (inputRangeMax - inputRangeMin) + outputRangeMin;
    }
}
