package com.sevanetrebchenko;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import java.awt.*;
import java.io.File;

import static org.lwjgl.opengl.GL11.*;

public class AlgorithmsGraphics extends Thread {
    private Dimension screenSize;
    private Algorithms algorithms;
    private AlgorithmsThread current;
    private final Object lock = new Object();

    private boolean threadStarted = false;
    private boolean threadCompleted = false;
    private boolean destroy = false;

    AlgorithmsGraphics(Dimension dim) {
        this.screenSize = dim;
    }

    public void init() {
        // initialize display
        try {
            Display.setTitle("Visualizing Sorting Algorithms");
            Display.setDisplayMode(new DisplayMode(this.screenSize.width, this.screenSize.height));
            Display.create();

            if (!Keyboard.isCreated()) {
                Keyboard.create();
            }
            if (!Mouse.isCreated()) {
                Keyboard.create();
            }

            glViewport(0, 0, Display.getWidth(), Display.getHeight());
            glLoadIdentity();
            glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
        }

        catch (LWJGLException e) {
            System.out.println(e.toString());
        }

        File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\random1000.txt");
        Pair<Object, Boolean> multithreading = new Pair<Object, Boolean>(this.lock, true);
        this.algorithms = new Algorithms(file, multithreading);
    }

    public void run() {
        // While the Display is still active on the screen.
        while (!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT);
            glColor3f(255, 255, 255);

            processInput();

            if (threadStarted) {
                // sorting thread
                if (current.getType() != AlgorithmsThread.SortingAlgorithm.SHUFFLE) {
                    if (!current.isSorted()) {
                        synchronized (lock) {
                            update(algorithms.getData());
                            lock.notify();
                        }
                    } else {
                        threadStarted = false;
                        threadCompleted = true;
                        current.interrupt();
                    }
                }
                // shuffling thread
                else {
                    if (!current.isShuffled()) {
                        synchronized (lock) {
                            update(algorithms.getData());
                            lock.notify();
                        }
                    }
                    else {
                        current.shuffle();
                        threadStarted = false;
                        threadCompleted = true;
                        current.interrupt();
                    }
                }
            }
            else if (threadCompleted) {
                if (current != null) {
                    if (current.getType() != AlgorithmsThread.SortingAlgorithm.SHUFFLE) {
                        glColor3f(0, 255, 0);
                    }
                    update(algorithms.getData());
                }
            }

            else {
                update(algorithms.getData());
            }

            Display.update();
            Display.sync(60);

            if (this.destroy) {
                break;
            }
        }

        this.clean();
    }

    private void processInput() {
        while (Keyboard.next()) {
            // selection sort
            if (Keyboard.getEventKey() == Keyboard.KEY_1 && !Keyboard.getEventKeyState()) {
                if (!threadStarted) {
                    this.startThread(AlgorithmsThread.SortingAlgorithm.SELECTION);
                }
            }

            // bubble sort
            if (Keyboard.getEventKey() == Keyboard.KEY_2 && !Keyboard.getEventKeyState()) {
                if (!threadStarted) {
                    this.startThread(AlgorithmsThread.SortingAlgorithm.BUBBLE);
                }
            }

            // insertion sort
            if (Keyboard.getEventKey() == Keyboard.KEY_3 && !Keyboard.getEventKeyState()) {
                if (!threadStarted) {
                    this.startThread(AlgorithmsThread.SortingAlgorithm.INSERTION);
                }
            }

            // shuffle data
            if (Keyboard.getEventKey() == Keyboard.KEY_S && !Keyboard.getEventKeyState()) {
                if (!threadStarted) {
                    this.startThread(AlgorithmsThread.SortingAlgorithm.SHUFFLE);
                }
            }

            // exit
            if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && !Keyboard.getEventKeyState()) {
                if (current != null) {
                    current.stopThread();
                }
                this.destroy = true;
                return;
            }
        }
    }

    private void clean() {
        if (Keyboard.isCreated()) {
            Keyboard.destroy();
        }
        if (Mouse.isCreated()) {
            Mouse.destroy();
        }

        Display.destroy();
    }

    private void startThread(AlgorithmsThread.SortingAlgorithm algorithm) {
        if (algorithms.isSorted() && algorithm != AlgorithmsThread.SortingAlgorithm.SHUFFLE) {
            System.out.println("Data is sorted.");
            return;
        }

        // start thread
        this.current = new AlgorithmsThread(this.algorithms, algorithm);
        current.setName(this.current.getAlgorithm() + " THREAD");
        current.start();
        threadStarted = true;
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
        int rectangleWidth = Math.round(Display.getWidth() / (float)data.length);
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
