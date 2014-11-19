package com.n8lm.zener.app;

import com.n8lm.zener.data.ResourceManager;
import org.lwjgl.Sys;

import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created on 2014/11/2.
 *
 * @author Alchemist
 */
public abstract class Container {

    private final static Logger LOGGER = Logger.getLogger(Container.class
            .getName());
    /** The time the last frame was rendered */
    protected long lastFrame;
    /** The last time the FPS recorded */
    protected long lastFPS;
    /** The last recorded FPS */
    protected int recordedFPS;
    /** The current count of FPS */
    protected int fps;
    /** True if we're currently running the game loop */
    protected boolean running = true;

    /**
     * Get the build number of zener
     *
     * @return The build number of zener
     */
    public static String getBuildVersion() {
        try {
			Properties props = new Properties();
			props.load(ResourceManager.getInstance().getResourceAsStream(
					"version"));

            String build = props.getProperty("build");
            //int build = Integer.parseInt(props.getProperty("build"));
            LOGGER.info("Zener Build #" + build);

            return build;
        } catch (Exception e) {
            LOGGER.severe("Unable to determine Zener build number");
            return "";
        }
    }

    /**
     * Get the accurate system time
     *
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Sleep for a given period
     *
     * @param milliseconds
     *            The period to sleep for in milliseconds
     */
    public void sleep(int milliseconds) {
        long target = getTime() + milliseconds;
        while (getTime() < target) {
            try {
                Thread.sleep(1);
            } catch (Exception e) {
            }
        }
    }

    /**
     * Get the current recorded FPS (frames per second)
     *
     * @return The current FPS
     */
    public int getFPS() {
        return recordedFPS;
    }

    /**
     * Retrieve the time taken to render the last frame, i.e. the change in time
     * - delta.
     *
     * @return The time taken to render the last frame
     */
    protected int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    /**
     * Updated the FPS counter
     */
    protected void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            lastFPS += 1000;
            recordedFPS = fps;
            fps = 0;
        }
        fps++;
    }

    /**
     * True if the game is running
     *
     * @return True if the game is running
     */
    public boolean running() {
        return running;
    }

    /**
     * Cause the game to exit and shutdown cleanly
     */
    public void exit() {
        running = false;
    }

}
