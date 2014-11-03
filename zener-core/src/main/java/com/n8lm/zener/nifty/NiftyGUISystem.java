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

package com.n8lm.zener.nifty;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.util.List;

import de.lessvoid.nifty.renderer.lwjgl.input.LwjglInputSystem;
import de.lessvoid.nifty.renderer.lwjgl.render.LwjglBatchRenderBackendFactory;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.artemis.systems.VoidEntitySystem;
import com.n8lm.zener.app.BasicGame;
import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.intent.InputIntentGenerator;
import com.n8lm.zener.nifty.input.ZenerInputSystem;
import com.n8lm.zener.nifty.input.ZenerZenerInputSystem;

import de.lessvoid.nifty.Nifty;
//import de.lessvoid.nifty.renderer.lwjgl.render.LwjglBatchRenderBackendCoreProfileFactory;
//import de.lessvoid.nifty.renderer.lwjgl.render.LwjglRenderDevice;
import de.lessvoid.nifty.render.batch.BatchRenderDevice;
import com.n8lm.zener.nifty.audio.OpenALSoundDevice;
import de.lessvoid.nifty.spi.time.impl.AccurateTimeProvider;

public class NiftyGUISystem extends VoidEntitySystem {
	
	private BasicGame game;
	private Nifty nifty;
	//private ZenerInputSystem inputSystem;
	private InputIntentGenerator iig;
	private String startScreen;
	
	public NiftyGUISystem(BasicGame game, InputIntentGenerator iig, String startScreen) {
		super();
		this.game = game;
		this.iig = iig;
		this.startScreen = startScreen;

        ZenerInputSystem inputSystem = new ZenerZenerInputSystem(iig);
		inputSystem.setInput(game.getContainer().getInput());
		
		game.getContainer().getInput().addListener(inputSystem);

        BatchRenderDevice renderDevice = new BatchRenderDevice(LwjglBatchRenderBackendFactory.create());
        //BatchRenderDevice renderDevice = new BatchRenderDevice(LwjglBatchRenderBackendCoreProfileFactory.create());

		nifty = new Nifty(
                //new LwjglRenderDevice(),
                renderDevice,
		        new OpenALSoundDevice(),
		        inputSystem,
		        new AccurateTimeProvider());

	}

    public NiftyGUISystem(BasicGame game, String startScreen) {
        super();
        this.game = game;
        this.startScreen = startScreen;

        BatchRenderDevice renderDevice = new BatchRenderDevice(LwjglBatchRenderBackendFactory.create());
        //BatchRenderDevice renderDevice = new BatchRenderDevice(LwjglBatchRenderBackendCoreProfileFactory.create());

        nifty = new Nifty(
                //new LwjglRenderDevice(),
                renderDevice,
                new OpenALSoundDevice(),
                new LwjglInputSystem(),
                new AccurateTimeProvider());
    }

    @Override
	protected void processSystem() {

        glViewport(0, 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());
        
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, game.getContainer().getWidth(), game.getContainer().getHeight(), 0, -9999, 9999);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();  
		
		glPushAttrib(GL_ENABLE_BIT);
		  
		GL11.glDisable(GL11.GL_DEPTH_TEST);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_NOTEQUAL, 0);
		
		GL11.glDisable(GL11.GL_LIGHTING);

        // Back to GL Texture 0 Unit
        glActiveTexture(GL_TEXTURE0);
        glEnable(GL_TEXTURE_2D);
        //glBindTexture();

        nifty.update();
		nifty.render(false);
		  
		glPopAttrib();
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
	}

	@Override
	protected void initialize() {
		super.initialize();
		
		//this.screenCallbacks.clear();

	    /*final HelloWorldStartScreen screen = new HelloWorldStartScreen();
	    nifty.registerScreenController(screen);

	    screen.prepareStart(nifty);
	    */
		List<String> xmls = ResourceManager.getInstance().getGUIXmls();
		for (String xml : xmls)
			nifty.addXml(ResourceManager.getInstance().getResourceAsStream(xml));
	    nifty.gotoScreen(startScreen);
	}
	
	public String getStartScreen() {
		return startScreen;
	}

	public void setStartScreen(String startScreen) {
		this.startScreen = startScreen;
	}

	public Nifty getNifty() {
		return nifty;
	}

    public InputIntentGenerator getInputIntentGenerator() {
        return iig;
    }

	/*public void addCallback(GameCallback callback) {
		screenCallbacks.add(callback);
	}*/
}
