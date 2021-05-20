package Engine;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import Entity.*;
import Services.Level;
import Services.LevelLoader;

public class MainEngine {
    private LevelLoader levelLoader;
    private int[] windowSize;
    private long window;


    public MainEngine(int windowWidth, int windowHeight) {
        // ini all vars
        windowSize = new int[]{windowWidth, windowHeight};
    }

    public void startRendering(String level) {
        levelLoader = new LevelLoader(level);
        shiftInLevel();
        // mainLoop();
        testLoop();
    }

    //used for loading
    private void shiftInLevel() {

    }

    //used for loading
    private void shiftOutOfLevel() {

    }

    private boolean changeLevel() {
        return false;
    }

    private void openGlInitCode() {
        if (!glfwInit()) {
            System.out.print("Error in ini of GLFW");
        }
        long win = glfwCreateWindow(windowSize[0], windowSize[1], "Game", 0, 0);
        window = win;
        glfwShowWindow(win);
        glfwMakeContextCurrent(win);
        GL.createCapabilities();
        glEnable(GL_TEXTURE_2D);
        glMatrixMode(GL_PROJECTION); // projection matrix defines the properties of the camera that views the objects in the world coordinate frame. Here you typically set the zoom factor, aspect ratio and the near and far clipping planes
        glLoadIdentity(); // replace the current matrix with the identity matrix and starts us a fresh because matrix transforms such as glOrpho and glRotate cumulate, basically puts us at (0, 0, 0)
        glOrtho(0, windowSize[0], 0, windowSize[1], 0, 1); // essentially set coordinate system
        glMatrixMode(GL_MODELVIEW); // (default matrix mode) modelview matrix defines how your objects are transformed (meaning translation, rotation and scaling) in your world
        glLoadIdentity(); // same as above comment
        glEnable(GL11.GL_BLEND);
    }


    private void testLoop() {
        openGlInitCode();
        GenericEntity e1 = new GenericEntity(100, 100, new Location(100, 200));
        while (!glfwWindowShouldClose(window)) {

            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            GL11.glColor3f(0, 1, 1);
            e1.draw();
            e1.setRotation(45);
            e1.moveBy(0.1f, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);


            glfwSwapBuffers(window);


        }
        shiftOutOfLevel();
    }


    //not used 
    private void mainLoop() {
        openGlInitCode();
        Level level = levelLoader.get();
        while (!changeLevel() && !glfwWindowShouldClose(window)) {
            /*
             * Need to split processes into thread here
             * 1 for rendering
             * 1 for math
             * 1 for collision and path finding
             * 1 for input checking
             *	ect...
             */
            level.render();
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            glfwSwapBuffers(window);
            shiftOutOfLevel();

        }
    }
}
