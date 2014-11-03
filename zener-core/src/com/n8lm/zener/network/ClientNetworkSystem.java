package com.n8lm.zener.network;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created on 2014/9/18.
 *
 * @author Alchemist
 */
public class ClientNetworkSystem extends EntityProcessingSystem {

    private final static Logger logger = Logger.getLogger(ServerNetworkSystem.class.getName());

    private Client client;

    private NetworkConfiguration config;
    private NetworkMessageAdapter networkMessageAdapter;

    public ClientNetworkSystem(NetworkConfiguration config, NetworkMessageAdapter networkMessageAdapter) {
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

    }

    public void connet(String host) {

        client = new Client();
        client.start();

        config.register(client);
        networkMessageAdapter.init(client);

        client.addListener(new Listener.ThreadedListener(new NetworkListener(config, networkMessageAdapter)));
        try {
            client.connect(5000, host, config.getServerPort());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can not bind port", e);
            //e.printStackTrace();
        }
    }

    @Override
    protected void end() {
        super.end();
    }

    @Override
    protected void process(Entity e) {

    }

    public void sendMessage(NetworkMessage message) {
        client.sendTCP(message);
    }

    public void sendImportantMessage(NetworkMessage message) {
        client.sendTCP(message);
    }
}
