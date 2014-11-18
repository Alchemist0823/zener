package com.n8lm.zener.network;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.VoidEntitySystem;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created on 2014/9/18.
 *
 * @author Alchemist
 */
public class ClientNetworkSystem extends VoidEntitySystem {

    private final static Logger logger = Logger.getLogger(ServerNetworkSystem.class.getName());

    private Client client;

    private final boolean sync;
    private WaitingQueuedListener queuedListener;

    private NetworkConfiguration config;
    private NetworkMessageAdapter networkMessageAdapter;

    public ClientNetworkSystem(boolean sync, NetworkConfiguration config, NetworkMessageAdapter networkMessageAdapter) {
        this.sync = sync;
        this.networkMessageAdapter = networkMessageAdapter;
        this.config = config;
    }

    public void setConfig(NetworkConfiguration config) {
        this.config = config;
    }

    public void setNetworkMessageAdapter(NetworkMessageAdapter networkMessageAdapter) {
        this.networkMessageAdapter = networkMessageAdapter;
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

        if (sync) {
            queuedListener = new WaitingQueuedListener(new NetworkListener(config, networkMessageAdapter));
            client.addListener(queuedListener);
        } else
            client.addListener(new Listener.ThreadedListener(new NetworkListener(config, networkMessageAdapter)));
        try {
            client.connect(5000, host, config.getServerTCPPort(), config.getServerUDPPort());
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
    protected void processSystem() {
        if (sync) {
            ExecutorService es = Executors.newFixedThreadPool(1);
            while (!queuedListener.isEmpty()) {
                es.execute(queuedListener.pop());
                //System.out.println("aaaa");
            }
            es.shutdown();
            try {
                es.awaitTermination(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(NetworkMessage message) {
        client.sendUDP(message);
    }

    public void sendImportantMessage(NetworkMessage message) {
        client.sendTCP(message);
    }

}
