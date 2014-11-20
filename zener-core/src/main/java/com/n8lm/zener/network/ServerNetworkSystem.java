package com.n8lm.zener.network;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.VoidEntitySystem;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ServerNetworkSystem is a VoidEntitySystem which processes the requests
 * from clients and sends data to clients. It runs a Kyronet server on another
 * thread and processes all the requests on that thread.
 *
 * Created on 2014/9/17.
 * @author Forrest Sun
 */
public class ServerNetworkSystem extends VoidEntitySystem {

    private final static Logger logger = Logger.getLogger(ServerNetworkSystem.class.getName());

    NetworkConfiguration config;
    NetworkMessageAdapter networkMessageAdapter;

    public ServerNetworkSystem(NetworkConfiguration config, NetworkMessageAdapter networkMessageAdapter) {
        this.networkMessageAdapter = networkMessageAdapter;
        this.config = config;
        this.setPassive(true);
    }

    @Override
    protected void initialize() {
        super.initialize();

        Server server = new Server();

        config.register(server);
        networkMessageAdapter.init(server);
        server.addListener(new NetworkListener(config, networkMessageAdapter));

        try {
            server.bind(config.getServerTCPPort(), config.getServerUDPPort());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can not bind port", e);
            //e.printStackTrace();
        }
        server.start();
        //logger.log(Level.INFO, "start server at " + config.getServerPort());
    }

    @Override
    protected void processSystem() {

    }
}
