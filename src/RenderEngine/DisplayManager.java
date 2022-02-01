package RenderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;

public class DisplayManager {

    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1280;
    private static final int FPS_LIMIT = 120;

    private static long lastFrameTime;
    private static float delta;

    public static void createDisplay() {
        Display.setTitle("ThinMatrix");
        ContextAttribs attribs = new ContextAttribs(3, 2)
        .withForwardCompatible(true).withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), attribs);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        lastFrameTime = getCurrentTime();
    }

    public static void updateDisplay() {
        Display.sync(FPS_LIMIT);
        Display.update();
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime) / 1000f;
        lastFrameTime = currentFrameTime;
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                    closeDisplay();
                }
            }
        }
    }

    public static float getFrameTimeSeconds(){
        return delta;
    }

    public static void closeDisplay() {
        Display.destroy();
        System.exit(0);
    }

    private static long getCurrentTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }
}
