/*
 * This file is part of Zener.
 *
 * Zener is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * Zener is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with Zener.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.n8lm.zener.app;

import java.util.logging.Logger;

import com.n8lm.zener.audio.Music;
import com.n8lm.zener.graphics.GLRenderSystem;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.Drawable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.opengl.Pbuffer;
import com.n8lm.zener.audio.openal.SoundStore;

import com.n8lm.zener.input.Input;
import com.n8lm.zener.utils.ZenerException;

public abstract class AbstractAppContainer extends Container{

	private final static Logger LOGGER = Logger.getLogger(AbstractAppContainer.class
			.getName());

	/** The width of the display */
	protected int width;
	/** The height of the display */
	protected int height;
	/** The game being managed */
	protected BasicApp game;
	/** The shared drawable if any */
	protected static Drawable SHARED_DRAWABLE;

	/** The input system to pass to the game */
	protected Input input;
	/** The FPS we want to lock to */
	protected int targetFPS = -1;
	/** True if we should show the fps */
	private boolean showFPS = true;
	/** The minimum logic update interval */
	protected long minimumLogicInterval = 1;
	/** The stored delta */
	protected long storedDelta;
	/** The maximum logic update interval */
	protected long maximumLogicInterval = 0;
	/** True if we should clear the screen each frame */
	protected boolean clearEachFrame = true;

	/** True if the game is paused */
	protected boolean paused;
	/** True if we should force exit */
	protected boolean forceExit = true;
	/** True if vsync has been requested */
	protected boolean vsync;
	/** Smoothed deltas requested */
	protected boolean smoothDeltas;
	/** The number of samples we'll attempt through hardware */
	protected int samples;

	/** True if this context supports multisample */
	protected boolean supportsMultiSample;

	/** True if we should render when not focused */
	protected boolean alwaysRender;
	/** True if we require stencil bits */
	protected static boolean stencil;

	/** SoundManager to make sound with */
	// private SoundManager soundManager;

	/** Is this an application or applet */
	private static boolean isApplication;

	/**
	 * Construct our game and set it running.
	 *
	 * 
	 */
	public AbstractAppContainer(BasicApp game) {
		this.game = game;
		lastFrame = getTime();

		getBuildVersion();
		// Log.checkVerboseLogSetting();
	}

	public static void enableStencil() {
		stencil = true;
	}

	/**
	 * Called to clean up the program's memory. Destroys AL and Display if
	 * either is created.
	 */
	public void destroy() {
		//InternalTextureLoader.get().clear();
		SoundStore.get().clear();
		if (Display.isCreated())
			Display.destroy();
		if (AL.isCreated())
			AL.destroy();
	}

	/**
	 * Indicate whether we want to try to use fullscreen multisampling. This
	 * will give antialiasing across the whole scene using a hardware feature.
	 * 
	 * @param samples
	 *            The number of samples to attempt (2 is safe)
	 */
	public void setMultiSample(int samples) {
		this.samples = samples;
	}

	/**
	 * Check if this hardware can support multi-sampling
	 * 
	 * @return True if the hardware supports multi-sampling
	 */
	public boolean supportsMultiSample() {
		return supportsMultiSample;
	}

	/**
	 * The number of samples we're attempting to performing using hardware
	 * multisampling
	 * 
	 * @return The number of samples requested
	 */
	public int getSamples() {
		return samples;
	}

	/**
	 * Indicate if we should force exitting the VM at the end of the game
	 * (default = true)
	 * 
	 * @param forceExit
	 *            True if we should force the VM exit
	 */
	public void setForceExit(boolean forceExit) {
		this.forceExit = forceExit;
	}

	/**
	 * Indicate if we want to smooth deltas. This feature will report a delta
	 * based on the FPS not the time passed. This works well with vsync.
	 * 
	 * @param smoothDeltas
	 *            True if we should report smooth deltas
	 */
	public void setSmoothDeltas(boolean smoothDeltas) {
		this.smoothDeltas = smoothDeltas;
	}

	/**
	 * Check if the display is in fullscreen mode
	 * 
	 * @return True if the display is in fullscreen mode
	 */
	public boolean isFullscreen() {
		return false;
	}

	/**
	 * Get the aspect ratio of the screen
	 * 
	 * @return The aspect ratio of the display
	 */
	public float getAspectRatio() {
		return getWidth() / getHeight();
	}

	/**
	 * Indicate whether we want to be in fullscreen mode. Note that the current
	 * display mode must be valid as a fullscreen mode for this to work
	 * 
	 * @param fullscreen
	 *            True if we want to be in fullscreen mode
	 * @throws Exception
	 */
	public void setFullscreen(boolean fullscreen) throws Exception {
	}

	/**
	 * Enable shared OpenGL context. After calling this all containers created
	 * will shared a single parent context
	 * 
	 * @throws ZenerException
	 *             Indicates a failure to create the shared drawable
	 */
	public static void enableSharedContext() throws ZenerException {
		try {
			SHARED_DRAWABLE = new Pbuffer(64, 64, new PixelFormat(8, 0, 0),
					null);
		} catch (LWJGLException e) {
			throw new ZenerException(
					"Unable to create the pbuffer used for shard context, buffers not supported",
					e);
		}
	}

	/**
	 * Get the context shared by all containers
	 * 
	 * @return The context shared by all the containers or null if shared
	 *         context isn't enabled
	 */
	public static Drawable getSharedContext() {
		return SHARED_DRAWABLE;
	}

	/**
	 * Indicate if we should clear the screen at the beginning of each frame. If
	 * you're rendering to the whole screen each frame then setting this to
	 * false can give some performance improvements
	 * 
	 * @param clear
	 *            True if the the screen should be cleared each frame
	 */
	public void setClearEachFrame(boolean clear) {
		this.clearEachFrame = clear;
	}

	/**
	 * Renitialise the game and the context in which it's being rendered
	 * 
	 * @throws ZenerException
	 *             Indicates a failure rerun initialisation routines
	 */
	public void reinit() throws ZenerException {
	}

	/**
	 * Pause the game - i.e. suspend updates
	 */
	public void pause() {
		setPaused(true);
	}

	/**
	 * Resumt the game - i.e. continue updates
	 */
	public void resume() {
		setPaused(false);
	}

	/**
	 * Check if the container is currently paused.
	 * 
	 * @return True if the container is paused
	 */
	public boolean isPaused() {
		return paused;
	}

	/**
	 * Indicates if the game should be paused, i.e. if updates should be
	 * propogated through to the game.
	 * 
	 * @param paused
	 *            True if the game should be paused
	 */
	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	/**
	 * True if this container should render when it has focus
	 * 
	 * @return True if this container should render when it has focus
	 */
	public boolean getAlwaysRender() {
		return alwaysRender;
	}

	/**
	 * Indicate whether we want this container to render when it has focus
	 * 
	 * @param alwaysRender
	 *            True if this container should render when it has focus
	 */
	public void setAlwaysRender(boolean alwaysRender) {
		this.alwaysRender = alwaysRender;
	}

	/**
	 * Check if sound effects are enabled
	 * 
	 * @return True if sound effects are enabled
	 */
	public boolean isSoundOn() {
		return SoundStore.get().soundsOn();
	}

	/**
	 * Check if music is enabled
	 * 
	 * @return True if music is enabled
	 */
	public boolean isMusicOn() {
		return SoundStore.get().musicOn();
	}

	/**
	 * Indicate whether music should be enabled
	 * 
	 * @param on
	 *            True if music should be enabled
	 */
	public void setMusicOn(boolean on) {
		SoundStore.get().setMusicOn(on);
	}

	/**
	 * Indicate whether sound effects should be enabled
	 * 
	 * @param on
	 *            True if sound effects should be enabled
	 */
	public void setSoundOn(boolean on) {
		SoundStore.get().setSoundsOn(on);
	}

	/**
	 * Retrieve the current default volume for music
	 * 
	 * @return the current default volume for music
	 */
	public float getMusicVolume() {
		return SoundStore.get().getMusicVolume();
	}

	/**
	 * Retrieve the current default volume for sound fx
	 * 
	 * @return the current default volume for sound fx
	 */
	public float getSoundVolume() {
		return SoundStore.get().getSoundVolume();
	}

	/**
	 * Set the default volume for sound fx
	 * 
	 * @param volume
	 *            the new default value for sound fx volume
	 */
	public void setSoundVolume(float volume) {
		SoundStore.get().setSoundVolume(volume);
	}

	/**
	 * Set the default volume for music
	 * 
	 * @param volume
	 *            the new default value for music volume
	 */
	public void setMusicVolume(float volume) {
		SoundStore.get().setMusicVolume(volume);
	}

	/**
	 * Get the width of the standard screen resolution
	 * 
	 * @return The screen width
	 */
	public abstract int getScreenWidth();

	/**
	 * Get the height of the standard screen resolution
	 * 
	 * @return The screen height
	 */
	public abstract int getScreenHeight();

	/**
	 * Get the width of the game canvas
	 * 
	 * @return The width of the game canvas
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Get the height of the game canvas
	 * 
	 * @return The height of the game canvas
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Set the icon to be displayed if possible in this type of container
	 * 
	 * @param ref
	 *            The reference to the icon to be displayed
	 * @throws ZenerException
	 *             Indicates a failure to load the icon
	 */
	public abstract void setIcon(String ref) throws ZenerException;

	/**
	 * Set the icons to be used for this application. Note that the size of the
	 * icon defines how it will be used. Important ones to note
	 * 
	 * Windows window icon must be 16x16 Windows alt-tab icon must be 24x24 or
	 * 32x32 depending on Windows version (XP=32)
	 * 
	 * @param refs
	 *            The reference to the icon to be displayed
	 * @throws ZenerException
	 *             Indicates a failure to load the icon
	 */
	public abstract void setIcons(String[] refs) throws ZenerException;


	/**
	 * Set the mouse cursor to be displayed - this is a hardware cursor and
	 * hence shouldn't have any impact on FPS.
	 * 
	 * @param ref
	 *            The location of the image to be loaded for the cursor
	 * @param hotSpotX
	 *            The x coordinate of the hotspot within the cursor image
	 * @param hotSpotY
	 *            The y coordinate of the hotspot within the cursor image
	 * @throws ZenerException
	 *             Indicates a failure to load the cursor image or create the
	 *             hardware cursor
	 */
	/*
	public abstract void setMouseCursor(String ref, int hotSpotX, int hotSpotY)
			throws ZenerException;
	*/
	/**
	 * Set the mouse cursor to be displayed - this is a hardware cursor and
	 * hence shouldn't have any impact on FPS.
	 * 
	 * @param data
	 *            The image data from which the cursor can be construted
	 * @param hotSpotX
	 *            The x coordinate of the hotspot within the cursor image
	 * @param hotSpotY
	 *            The y coordinate of the hotspot within the cursor image
	 * @throws ZenerException
	 *             Indicates a failure to load the cursor image or create the
	 *             hardware cursor
	 */
	/*public abstract void setMouseCursor(ImageData data, int hotSpotX,
			int hotSpotY) throws ZenerException;*/

	/**
	 * Set the mouse cursor based on the contents of the image. Note that this
	 * will not take account of render state type changes to images (rotation
	 * and such). If these effects are required it is recommended that an
	 * offscreen buffer be used to produce an appropriate image. An offscreen
	 * buffer will always be used to produce the new cursor and as such this
	 * operation an be very expensive
	 * 
	 * @param image
	 *            The image to use as the cursor
	 * @param hotSpotX
	 *            The x coordinate of the hotspot within the cursor image
	 * @param hotSpotY
	 *            The y coordinate of the hotspot within the cursor image
	 * @throws ZenerException
	 *             Indicates a failure to load the cursor image or create the
	 *             hardware cursor
	 */
	// public abstract void setMouseCursor(Image image, int hotSpotX, int
	// hotSpotY) throws ZenerException;

	/**
	 * Set the mouse cursor to be displayed - this is a hardware cursor and
	 * hence shouldn't have any impact on FPS.
	 * 
	 * @param cursor
	 *            The cursor to use
	 * @param hotSpotX
	 *            The x coordinate of the hotspot within the cursor image
	 * @param hotSpotY
	 *            The y coordinate of the hotspot within the cursor image
	 * @throws ZenerException
	 *             Indicates a failure to load the cursor image or create the
	 *             hardware cursor
	 */
	/*
	public abstract void setMouseCursor(Cursor cursor, int hotSpotX,
			int hotSpotY) throws ZenerException;
	 */
    /*
	 * Get a cursor based on a image reference on the classpath. The image is
	 * assumed to be a set/strip of cursor animation frames running from top to
	 * bottom.
	 * 
	 * @param ref
	 *            The reference to the image to be loaded
	 * @param x
	 *            The x-coordinate of the cursor hotspot (left -> right)
	 * @param y
	 *            The y-coordinate of the cursor hotspot (bottom -> top)
	 * @param width
	 *            The x width of the cursor
	 * @param height
	 *            The y height of the cursor
	 * @param cursorDelays
	 *            image delays between changing frames in animation
	 * 
	 * @throws ZenerException
	 *             Indicates a failure to load the image or a failure to create
	 *             the hardware cursor

	public void setAnimatedMouseCursor(String ref, int x, int y, int width,
			int height, int[] cursorDelays) throws ZenerException {
		try {
			Cursor cursor;
			cursor = CursorLoader.get().getAnimatedCursor(ref, x, y, width,
					height, cursorDelays);
			setMouseCursor(cursor, x, y);
		} catch (IOException e) {
			throw new ZenerException("Failed to set mouse cursor", e);
		} catch (LWJGLException e) {
			throw new ZenerException("Failed to set mouse cursor", e);
		}
	}
	*/

	/**
	 * Set the default mouse cursor - i.e. the original cursor before any native
	 * cursor was set
	 */
	public abstract void setDefaultMouseCursor();

	/**
	 * Get the input system
	 * 
	 * @return The input system available to this game container
	 */
	public Input getInput() {
		return input;
	}

	/**
	 * Indicate whether mouse cursor should be grabbed or not
	 * 
	 * @param grabbed
	 *            True if mouse cursor should be grabbed
	 */
	public abstract void setMouseGrabbed(boolean grabbed);

	/**
	 * Check if the mouse cursor is current grabbed. This will cause it not to
	 * be seen.
	 * 
	 * @return True if the mouse is currently grabbed
	 */
	public abstract boolean isMouseGrabbed();

    /**
     * Set the minimum amount of time in milliseonds that has to pass before
     * update() is called on the container game. This gives a way to limit logic
     * updates compared to renders.
     *
     * @param interval
     *            The minimum interval between logic updates
     */
    public void setMinimumLogicUpdateInterval(int interval) {
        minimumLogicInterval = interval;
    }

    /**
     * Set the maximum amount of time in milliseconds that can passed into the
     * update method. Useful for collision detection without sweeping.
     *
     * @param interval
     *            The maximum interval between logic updates
     */
    public void setMaximumLogicUpdateInterval(int interval) {
        maximumLogicInterval = interval;
    }

    /**
	 * Update and render the game
	 * 
	 * @param delta
	 *            The change in time since last update and render
	 * @throws ZenerException
	 *             Indicates an internal fault to the game.
	 */
	protected void updateAndRender(int delta) throws ZenerException {
		if (smoothDeltas) {
			if (getFPS() != 0) {
				delta = 1000 / getFPS();
			}
		}

		input.poll(width, height);

		Music.poll(delta);

		if (clearEachFrame) {
            GLRenderSystem.clear();
        }

		if (!paused) {
			storedDelta += delta;

			if (storedDelta >= minimumLogicInterval) {
				try {
					if (maximumLogicInterval != 0) {
						long cycles = storedDelta / maximumLogicInterval;
						for (int i = 0; i < cycles; i++) {
							game.update(this, (int) maximumLogicInterval);
						}

						int remainder = (int) (storedDelta % maximumLogicInterval);
						if (remainder > minimumLogicInterval) {
							game.update(this,
									(int) (remainder % maximumLogicInterval));
							storedDelta = 0;
						} else {
							storedDelta = remainder;
						}
					} else {
						game.update(this, (int) storedDelta);
						storedDelta = 0;
					}

				} catch (Throwable e) {
					LOGGER.severe(e.toString());
					e.printStackTrace();
					throw new ZenerException(
							"Game.update() failure - check the game code.");
				}
			}
		} else if (hasFocus() || getAlwaysRender()) {
			game.update(this, 0);
		}

		if (targetFPS != -1) {
			Display.sync(targetFPS);
		}
	}

	/**
	 * Indicate if the display should update only when the game is visible (the
	 * default is true)
	 * 
	 * @param updateOnlyWhenVisible
	 *            True if we should updated only when the display is visible
	 */
	public void setUpdateOnlyWhenVisible(boolean updateOnlyWhenVisible) {
	}

	/**
	 * Check if this game is only updating when visible to the user (default =
	 * true)
	 * 
	 * @return True if the game is only updated when the display is visible
	 */
	public boolean isUpdatingOnlyWhenVisible() {
		return true;
	}

	/**
	 * Called when the GL context is resized. This does not resize the GL view;
	 * instead, it simply re-enters orthographic projection.
	 */
	protected void onResize() {
		if (getInput() != null) {
			getInput().init(getHeight());
		}

		GL11.glViewport(0, 0, width, height);
	}

	/**
	 * Initialize the GL context
	 */
	protected void initGL() {
		LOGGER.info("Starting display " + width + " x " + height);
		initDisplay(width, height);

		if (input == null) {
			input = new Input(height);
		}
		input.init(height);
		// no need to remove listeners?
		// input.removeAllListeners();
	}

	/**
	 * Initializes default states (texturing, shade model, disable depth test,
	 * etc).
	 *
	 */
	public void initDisplay(int width, int height) {
		this.width = width;
		this.height = height;

		// glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
	}

	/**
	 * Initialize the system components, OpenGL and OpenAL.
	 * 
	 * @throws ZenerException
	 *             Indicates a failure to create a native handler
	 */
	protected void initSystem() {
		initGL();
		//setMusicVolume(1.0f);
		//setSoundVolume(1.0f);

		lastFPS = getTime();
	}

	/**
	 * Indicate whether the container should show the FPS
	 * 
	 * @param show
	 *            True if the container should show the FPS
	 */
	public void setShowFPS(boolean show) {
		showFPS = show;
	}

	/**
	 * Check if the FPS is currently showing
	 * 
	 * @return True if the FPS is showing
	 */
	public boolean isShowingFPS() {
		return showFPS;
	}

	/**
	 * Set the target fps we're hoping to get
	 * 
	 * @param fps
	 *            The target fps we're hoping to get
	 */
	public void setTargetFrameRate(int fps) {
		targetFPS = fps;
	}

	/**
	 * Indicate whether the display should be synced to the vertical refresh
	 * (stops tearing)
	 * 
	 * @param vsync
	 *            True if we want to sync to vertical refresh
	 */
	public void setVSync(boolean vsync) {
		this.vsync = vsync;
		Display.setVSyncEnabled(vsync);
	}

	/**
	 * True if vsync is requested
	 * 
	 * @return True if vsync is requested
	 */
	public boolean isVSyncRequested() {
		return vsync;
	}

	/**
	 * Inidcate we want verbose logging
	 * 
	 * @param verbose
	 *            True if we want verbose logging (INFO and DEBUG)
	 */
	/*
	 * public void setVerbose(boolean verbose) { Log.setVerbose(verbose); }
	 */
	/**
	 * Check if the game currently has focus
	 * 
	 * @return True if the game currently has focus
	 */
	public abstract boolean hasFocus();

}
