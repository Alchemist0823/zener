package com.n8lm.zener.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

/**
 * Created on 2014/9/17.
 *
 * @author Alchemist
 */
public interface NetworkMessageAdapter {
    void init(Server server);
    void init(Client client);
    void process(Connection connection, NetworkMessage message);

    void connected(Connection connection);

    void disconnected(Connection connection);
}
