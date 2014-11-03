package com.n8lm.zener.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * Created on 2014/9/17.
 *
 * @author Alchemist
 */
public class NetworkListener extends Listener{

    private NetworkConfiguration config;
    private NetworkMessageAdapter networkMessageAdapter;

    public NetworkListener(NetworkConfiguration config, NetworkMessageAdapter networkMessageAdapter) {
        this.config = config;
        this.networkMessageAdapter = networkMessageAdapter;
    }

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
        networkMessageAdapter.connected(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        networkMessageAdapter.disconnected(connection);
        super.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object object) {
        for (NetworkConfiguration.MessageType messageType : config.getMessageTypes())
            if (messageType.getMessageClass().isInstance(object)) {
                networkMessageAdapter.process(connection, messageType.getMessageClass().cast(object));
            }
    }
}
