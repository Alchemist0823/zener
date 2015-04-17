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

import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import com.n8lm.zener.assets.PNGLoader;
import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.general.ZenerException;



/**
 * A app container that will display the app as an stand alone
 * application.
 *
 * @author kevin, Forrest Sun
 */
public class AppContainer extends AbstractAppContainer {
	

	  private final static Logger LOGGER = Logger.getLogger(AppContainer.class
	      .getName());
	
	static {
		AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
        		try {
        			Display.getDisplayMode();
        		} catch (Exception e) {
        			LOGGER.severe(e.toString());
        		}
				return null;
            }});
	}
	
	/** The original display mode before we tampered with things */
	protected DisplayMode originalDisplayMode;
	/** The display mode we're going to try and use */
	protected DisplayMode targetDisplayMode;
	/** True if we should update the game only when the display is visible */
	protected boolean updateOnlyOnVisible = true;
	/** Alpha background supported */
	protected boolean alphaSupport = false;
	/** Whether the native display is resizable. */
	protected boolean resizable = false;
	/** A flag that indicates whether the display was resized since last frame. */
	protected boolean wasResized = false;
	
	/**
	 * Create a new container wrapping a game
	 * 
	 * @param game The game to be wrapped
	 * @throws ZenerException Indicates a failure to initialise the display
	 */
	public AppContainer(BasicApp game) throws ZenerException {
		this(game,640,480,false);
	}

	/**
	 * Create a new container wrapping a game
	 * 
	 * @param game The game to be wrapped
	 * @param width The width of the display required
	 * @param height The height of the display required
	 * @param fullscreen True if we want fullscreen mode
	 * @throws ZenerException Indicates a failure to initialise the display
	 */
	public AppContainer(BasicApp game, int width, int height, boolean fullscreen) throws ZenerException {
		super(game);
		
		originalDisplayMode = Display.getDisplayMode();
		
		setDisplayMode(width,height,fullscreen);
	}
	
	/**
	 * Check if the display created supported alpha in the back buffer
	 * 
	 * @return True if the back buffer supported alpha
	 */
	public boolean supportsAlphaInBackBuffer() {
		return alphaSupport;
	}
	
	/**
	 * Set the title of the window
	 * 
	 * @param title The title to set on the window
	 */
	public void setTitle(String title) {
		Display.setTitle(title);
	}
	
	/**
	 * Whether the user can resize the display. 
	 * @param resizable true if the user can resize the display
	 */
	public void setResizable(boolean resizable) {
		Display.setResizable(resizable);
	}
	
	/**
	 * Returns true if this display can be resized. 
	 * @return whether the display is resizable
	 */
	public boolean isResizable() {
		return Display.isResizable();
	}
	
//	TODO: implement with a ContainerListener interface; 
	// i.e. containerResized, containerActivated, containerDeactivated
//	/**
//	 * Returns true if this display was resized since last loop.
//	 * @return
//	 */
//	public boolean wasResized() {
//		return Display.wasResized();
//	}
	
	/**
	 * Set the display mode to be used 
	 * 
	 * @param width The width of the display required
	 * @param height The height of the display required
	 * @param fullscreen True if we want fullscreen mode
	 * @throws ZenerException Indicates a failure to initialise the display
	 */
	public void setDisplayMode(int width, int height, boolean fullscreen) {
		if ((this.width == width) && (this.height == height) && (isFullscreen() == fullscreen)) {
			return;
		}
		/*
		Color oldBG = null;
		Graphics g = getGraphics();
		if (g!=null) {
			Graphics.setCurrent(g);
			oldBG = g.getBackground();
		}*/
		
		try {
			targetDisplayMode = null;
			if (fullscreen) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;
				
				for (int i=0;i<modes.length;i++) {
					DisplayMode current = modes[i];
					
					if ((current.getWidth() == width) && (current.getHeight() == height)) {
						if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}

						// if we've found a match for bpp and frequence against the 
						// original display mode then it's probably best to go for this one
						// since it's most likely compatible with the monitor
						if ((current.getBitsPerPixel() == originalDisplayMode.getBitsPerPixel()) &&
						    (current.getFrequency() == originalDisplayMode.getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else {
				targetDisplayMode = new DisplayMode(width,height);
			}
			
			if (targetDisplayMode == null) {
				LOGGER.severe("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
				return;
				//throw new ZenerException("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
			}
			
			this.width = width;
			this.height = height;

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);
			
			
			if (Display.isCreated()) {
				initGL();
				onResize();
			} 
			
			//initGL will reset the clear color... so let's reset it
			/*if (oldBG!=null && g!=null) {
				g.setBackground(oldBG);
			}*/
				
			/*
			if (targetDisplayMode.getBitsPerPixel() == 16) {
				InternalTextureLoader.get().set16BitMode();
			}*/
		} catch (LWJGLException e) {
			LOGGER.log(Level.SEVERE, "Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen, e);
			return;
		}
		
		getDelta();
	}
	
	/**
	 * Check if the display is in fullscreen mode
	 * 
	 * @return True if the display is in fullscreen mode
	 */
	public boolean isFullscreen() {
		return Display.isFullscreen();
	}
	
	/**
	 * Indicate whether we want to be in fullscreen mode. Note that the current
	 * display mode must be valid as a fullscreen mode for this to work
	 * 
	 * @param fullscreen True if we want to be in fullscreen mode
	 * @throws ZenerException Indicates we failed to change the display mode
	 */
	public void setFullscreen(boolean fullscreen) throws Exception {
		if (isFullscreen() == fullscreen) {
			return;
		}
		
		if (!fullscreen) {
			try {
				Display.setFullscreen(fullscreen);
			} catch (LWJGLException e) {
				throw new ZenerException("Unable to set fullscreen="+fullscreen, e);
			}
		} else {
			setDisplayMode(width, height, fullscreen);
		}
		getDelta();
	}

	
	/**
	 * @see com.n8lm.zener..GameContainer#setMouseCursor(java.lang.String, int, int)
	 */
	/*
	public void setMouseCursor(String ref, int hotSpotX, int hotSpotY) throws ZenerException {
		try {
			Cursor cursor = CursorLoader.get().getCursor(ref, hotSpotX, hotSpotY);
			Mouse.setNativeCursor(cursor);
		} catch (Throwable e) {
			LOGGER.log(Level.SEVERE, "Failed to load and apply cursor.", e);
			throw new ZenerException("Failed to set mouse cursor", e);
		}
	}*/
	
	/**
	 * @see com.n8lm.zener..GameContainer#setMouseCursor(com.n8lm.zener..opengl.ImageData, int, int)
	 */
	/*
	public void setMouseCursor(ImageData data, int hotSpotX, int hotSpotY) throws ZenerException {
		try {
			Cursor cursor = CursorLoader.get().getCursor(data, hotSpotX, hotSpotY);
			Mouse.setNativeCursor(cursor);
		} catch (Throwable e) {
			LOGGER.log(Level.SEVERE, "Failed to load and apply cursor.", e);
			throw new ZenerException("Failed to set mouse cursor", e);
		}
	}*/
	
	/**
	 * @see com.n8lm.zener..GameContainer#setMouseCursor(org.lwjgl.input.Cursor, int, int)
	 */
	/*
	public void setMouseCursor(Cursor cursor, int hotSpotX, int hotSpotY) throws ZenerException {
		try {
			Mouse.setNativeCursor(cursor);
		} catch (Throwable e) {
			LOGGER.log(Level.SEVERE, "Failed to load and apply cursor.", e);
			throw new ZenerException("Failed to set mouse cursor", e);
		}
	}*/

    /**
     * Get the closest greater power of 2 to the fold number
     * 
     * @param fold The target number
     * @return The power of 2
     */
	/*
    private int get2Fold(int fold) {
        int ret = 2;
        while (ret < fold) {
            ret *= 2;
        }
        return ret;
    }*/
    
	/**
	 * @see AbstractAppContainer#setMouseCursor(com.n8lm.zener.app.Image, int, int)
	 */
    /*
	public void setMouseCursor(Image image, int hotSpotX, int hotSpotY) throws ZenerException {
		try {
			Image temp = new Image(get2Fold(image.getWidth()), get2Fold(image.getHeight()));
			Graphics g = temp.getGraphics();
			
			ByteBuffer buffer = BufferUtils.createByteBuffer(temp.getWidth() * temp.getHeight() * 4);
			g.drawImage(image.getFlippedCopy(false, true), 0, 0);
			g.flush();
			g.getArea(0,0,temp.getWidth(),temp.getHeight(),buffer);
			
			Cursor cursor = CursorLoader.get().getCursor(buffer, hotSpotX, hotSpotY,temp.getWidth(),image.getHeight());
			Mouse.setNativeCursor(cursor);
		} catch (Throwable e) {
			LOGGER.severe("Failed to load and apply cursor.", e);
			throw new ZenerException("Failed to set mouse cursor", e);
		}
	}*/
	
	/**
	 * @see AbstractAppContainer#reinit()
	 */
	public void reinit() throws ZenerException {
		destroy();
		initSystem();
		
		//try {
			game.init(this);
		/*} catch (ZenerException e) {
			LOGGER.severe(e);
			running = false;
		}*/
	}
	
	/**
	 * Try creating a display with the given format
	 * 
	 * @param format The format to attempt
	 * @throws LWJGLException Indicates a failure to support the given format
	 */
	
	private void tryCreateDisplay(PixelFormat format) throws LWJGLException {
		if (SHARED_DRAWABLE == null) 
		{
			Display.create(format);
		}
		else
		{
			Display.create(format, SHARED_DRAWABLE);
		}
	}
	
	/**
	 * Start running the game
	 * 
	 * @throws ZenerException Indicates a failure to initialise the system
	 */
	public void start() throws ZenerException {
		try {
			setup();
			
			getDelta();
			while (running()) {
				gameLoop();
			}
		} finally {
			destroy();
		}
		
		if (forceExit) {
			System.exit(0);
		}
	}
	
	/**
	 * Setup the environment 
	 * 
	 * @throws ZenerException Indicates a failure
	 */
	protected void setup() throws ZenerException {
		if (targetDisplayMode == null) {
			setDisplayMode(640,480,false);
		}

		Display.setTitle(game.getTitle());

        LOGGER.info("Operation System:" + System.getProperty("os.name"));
        LOGGER.info("OS Architecture:" + System.getProperty("os.arch"));
		LOGGER.info("LWJGL Version: "+Sys.getVersion());
		LOGGER.info("OriginalDisplayMode: "+originalDisplayMode);
		LOGGER.info("TargetDisplayMode: "+targetDisplayMode);
		LOGGER.info("TargetSamples: "+samples);
		
		AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
        		try {
        			PixelFormat format = new PixelFormat(8, 8, stencil ? 8 : 0, samples);
        			
        			tryCreateDisplay(format);
        			supportsMultiSample = true;
        			LOGGER.info("Multi-Samples: "+samples);
        		} catch (Exception e) {
        			Display.destroy();
        			
        			try {
	        			PixelFormat format = new PixelFormat(8,8,stencil ? 8 : 0);
	        			
	        			tryCreateDisplay(format);
	        			alphaSupport = false;
        			} catch (Exception e2) {
	        			Display.destroy();
	        			// if we couldn't get alpha, let us know
		        		try {
		        			tryCreateDisplay(new PixelFormat());
		        		} catch (Exception e3) {
		        			LOGGER.log(Level.SEVERE, "exception" ,e3);
		        		}
        			}
        		}
				
				return null;
            }});
		
		if (!Display.isCreated()) {
			throw new ZenerException("Failed to initialise the LWJGL display");
		}
		
		initSystem();
		
		try {
			getInput().initControllers();
		} catch (ZenerException e) {
			LOGGER.info("Controllers not available");
		} catch (Throwable e) {
			LOGGER.info("Controllers not available");
		}
		
		//try {
			game.init(this);
		/*} catch (ZenerException e) {
			LOGGER.severe(e);
			running = false;
		}*/
	}
	
	/**
	 * Strategy for overloading game loop context handling
	 * 
	 * @throws ZenerException Indicates a game failure
	 */
	protected void gameLoop() throws ZenerException {
		int delta = getDelta();
		if (!Display.isVisible() && updateOnlyOnVisible) {
			try { Thread.sleep(100); } catch (Exception e) {}
		} else {
			if (Display.wasResized()) {
				width = Display.getWidth();
				height = Display.getHeight();
				onResize();
			}
			try {
				updateAndRender(delta);
			} catch (ZenerException e) {
				LOGGER.log(Level.SEVERE, "Exception", e);
				running = false;
				return;
			}
		}

		updateFPS();

		Display.update();
		
		if (Display.isCloseRequested() ||
			game.closeRequested()) {
				running = false;
		}
	}
	
	/**
	 * @see AbstractAppContainer#setUpdateOnlyWhenVisible(boolean)
	 */
	public void setUpdateOnlyWhenVisible(boolean updateOnlyWhenVisible) {
		updateOnlyOnVisible = updateOnlyWhenVisible;
	}
	
	/**
	 * @see AbstractAppContainer#isUpdatingOnlyWhenVisible()
	 */
	public boolean isUpdatingOnlyWhenVisible() {
		return updateOnlyOnVisible;
	}
	
	/**
	 * @see AbstractAppContainer#setIcon(java.lang.String)
	 */
	public void setIcon(String ref) throws ZenerException {
		setIcons(new String[] {ref});
	}

	/**
	 * @see AbstractAppContainer#setMouseGrabbed(boolean)
	 */
	public void setMouseGrabbed(boolean grabbed) {
		Mouse.setGrabbed(grabbed);
	}

	/**
	 * @see AbstractAppContainer#isMouseGrabbed()
	 */
	public boolean isMouseGrabbed() {
		return Mouse.isGrabbed();
	}
	
	/**
	 * @see AbstractAppContainer#hasFocus()
	 */
	public boolean hasFocus() {
		// hmm, not really the right thing, talk to the LWJGL guys
		return Display.isActive();
	}

	/**
	 * @see AbstractAppContainer#getScreenHeight()
	 */
	public int getScreenHeight() {
		return originalDisplayMode.getHeight();
	}

	/**
	 * @see AbstractAppContainer#getScreenWidth()
	 */
	public int getScreenWidth() {
		return originalDisplayMode.getWidth();
	}
	
	
	/**
	 * A null stream to clear out those horrid errors
	 *
	 * @author kevin
	 *//*
	private class NullOutputStream extends OutputStream {
		public void write(int b) throws IOException {
			// null implemetnation
		}
		
	}*/

	/**
	 * @see AbstractAppContainer#setIcons(java.lang.String[])
	 */
	/*
	public void setIcons(String[] refs) throws ZenerException {
		ByteBuffer[] bufs = new ByteBuffer[refs.length];
		for (int i=0;i<refs.length;i++) {
			LoadableImageData data;
			boolean flip = true;
			
			if (refs[i].endsWith(".tga")) {
				data = new TGAImageData();
			} else {
				flip = false;
				data = new ImageIOImageData();
			}
			
			try {
				bufs[i] = data.loadImage(ResourceManager.getInstance().getResourceAsStream(refs[i]), flip, false, null);
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Exception", e);
				throw new ZenerException("Failed to set the icon");
			}
		}
		
		Display.setIcon(bufs);
	}*/
	

	public void setIcons(String[] refs) throws ZenerException {
		ByteBuffer[] bufs = new ByteBuffer[refs.length];
		for (int i=0;i<refs.length;i++) {
			
			try {
				bufs[i] = PNGLoader.loadPNGImage(ResourceManager.getInstance().getResourceAsStream(refs[i])).getData();
				//bufs[i] = data.loadImage(ResourceManager.getInstance().getResourceAsStream(refs[i]), flip, false, null);
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Exception", e);
				throw new ZenerException("Failed to set the icon");
			}
		}
		
		Display.setIcon(bufs);
	}

	/**
	 * @see AbstractAppContainer#setDefaultMouseCursor()
	 */
	public void setDefaultMouseCursor() {
		try {
			Mouse.setNativeCursor(null);
		} catch (LWJGLException e) {
			LOGGER.log(Level.SEVERE, "Failed to reset mouse cursor", e);
		}
	}
}
