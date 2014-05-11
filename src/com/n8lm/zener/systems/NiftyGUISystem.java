package com.n8lm.zener.systems;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.artemis.systems.VoidEntitySystem;
import com.n8lm.zener.app.BasicGame;
import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.input.InputIntentGenerator;
import com.n8lm.zener.nifty.GameCallback;
import com.n8lm.zener.nifty.input.ZenerInputSystem;
import com.n8lm.zener.nifty.input.ZenerZenerInputSystem;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.renderer.lwjgl.render.LwjglRenderDevice;
import de.lessvoid.nifty.sound.openal.OpenALSoundDevice;
import de.lessvoid.nifty.spi.time.impl.AccurateTimeProvider;

public class NiftyGUISystem extends VoidEntitySystem {
	
	private BasicGame game;
	private Nifty nifty;
	private ZenerInputSystem inputSystem;
	private InputIntentGenerator iig;
	private List<GameCallback> screenCallbacks;
	private String startScreen;
	
	public NiftyGUISystem(BasicGame game, InputIntentGenerator iig, String startScreen) {
		super();
		this.game = game;
		this.iig = iig;
		this.screenCallbacks = new ArrayList<GameCallback>();
		this.startScreen = startScreen;
		
		inputSystem = new ZenerZenerInputSystem(iig);
		inputSystem.setInput(game.getContainer().getInput());
		
		game.getContainer().getInput().addListener(inputSystem);
		
		nifty = new Nifty(
		        new LwjglRenderDevice(),
		        new OpenALSoundDevice(),
		        inputSystem,
		        new AccurateTimeProvider());

	}

	@Override
	protected void processSystem() {

		if(game.isGameStarted()) {
			for(GameCallback scb: screenCallbacks) {
				scb.update(game);
			}
		}
		
        //glViewport(0, 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());
        
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, game.getContainer().getWidth(), game.getContainer().getHeight(), 0, -9999, 9999);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();  
		
		glPushAttrib(GL_ENABLE_BIT);
		  
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_NOTEQUAL, 0);
		
		GL11.glDisable(GL11.GL_LIGHTING);
		
		nifty.update();
		nifty.render(false);
		  
		glPopAttrib();
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		
	}

	@Override
	protected void initialize() {
		super.initialize();
		
		this.screenCallbacks.clear();

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

	public void addCallback(GameCallback callback) {
		screenCallbacks.add(callback);
	}
}