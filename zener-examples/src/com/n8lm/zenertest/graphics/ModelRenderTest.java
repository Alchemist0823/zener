package com.n8lm.zenertest.graphics;

import com.n8lm.zener.app.AppGameContainer;
import com.n8lm.zener.utils.ZenerException;
import com.n8lm.zenertest.ExampleBasicGame;

/**
 * Created by Alchemist on 2014/7/4.
 */
public class ModelRenderTest extends ExampleBasicGame{

    public ModelRenderTest() {
        super("modelrender", "Model Render");
    }

    public static void main(String[] args) throws ZenerException {
        ModelRenderTest game = new ModelRenderTest();
        AppGameContainer container = new AppGameContainer(game);
        container.setDisplayMode(1024, 768, false);
        container.setAlwaysRender(true);
        //container.setVSync(true);
        container.setTargetFrameRate(60);
        container.start();
    }
}
