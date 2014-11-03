package com.n8lm.zener.app;

import com.n8lm.zener.utils.ZenerException;
import org.lwjgl.Sys;

import java.util.logging.Logger;

/**
 * Created on 2014/11/1.
 *
 * @author Alchemist
 */
public class ServerContainer extends Container{

    private static Logger LOGGER = Logger.getLogger(ServerContainer.class.getName());

    private BasicServer server;

    public ServerContainer(BasicServer server) {
        this.server = server;
        lastFrame = getTime();

        getBuildVersion();
    }

    public void setup() {
        LOGGER.info("Operation System:" + System.getProperty("os.name"));
        LOGGER.info("OS Architecture:" + System.getProperty("os.arch"));
        LOGGER.info("LWJGL Version: "+ Sys.getVersion());

        server.init(this);
    }

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
    }

    /**
     * Strategy for overloading game loop context handling
     *
     * @throws ZenerException Indicates a game failure
     */
    protected void gameLoop() throws ZenerException {
        int delta = getDelta();
        server.update(delta);
    }

    protected void destroy(){

    }


}
