package com.sevanetrebchenko;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class AlgorithmsGraphics {
    private Dimension screenSize;

    AlgorithmsGraphics(Dimension dim) {
        this.screenSize = dim;
        this.init();
    }

    private void init() {
        try {
            Display.setTitle("Visualizing Sorting Algorithms");
            Display.setDisplayMode(new DisplayMode(this.screenSize.width, this.screenSize.height));
            Display.create();
            GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
            glLoadIdentity();
            GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        }

        catch (LWJGLException e) {
            System.out.println(e.toString());
        }
    }

    public void run() {
        // While the Display is still active on the screen.
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            glColor3f(255, 255, 255);
            this.update();
            Display.update();
            Display.sync(60);
        }
    }

    public void update() {
        glBegin(GL_QUADS);
            glVertex2i(150, 150);
            glVertex2i(200, 150);
            glVertex2i(200, 100);
            glVertex2i(150, 100);
        glEnd();
    }
}
