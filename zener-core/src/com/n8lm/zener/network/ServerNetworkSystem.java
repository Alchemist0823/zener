package com.n8lm.zener.network;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created on 2014/9/17.
 *
 * @author Alchemist
 */
public class ServerNetworkSystem extends EntityProcessingSystem {

    private final static Logger logger = Logger.getLogger(ServerNetworkSystem.class.getName());

    NetworkConfiguration config;
    NetworkMessageAdapter networkMessageAdapter;

    public ServerNetworkSystem(NetworkConfiguration config, NetworkMessageAdapter networkMessageAdapter) {
        super(Aspect.getAspectForAll(NetworkComponent.class));
        this.networkMessageAdapter = networkMessageAdapter;
        this.config = config;
    }

    @Override
    protected void begin() {
        super.begin();
    }

    @Override
    protected void initialize() {
        super.initialize();

        Server server = new Server();
        try {
            server.bind(config.getServerPort());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can not bind port", e);
            //e.printStackTrace();
        }
        server.addListener(new NetworkListener(config, networkMessageAdapter));
        server.start();
    }

    @Override
    protected void end() {
        super.end();
    }

    @Override
    protected void process(Entity e) {

    }
}
