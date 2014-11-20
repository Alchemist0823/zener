package com.n8lm.zener.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

/**
 * NetworkMessageAdapter is an interface for the user to implement.
 * It is used to process the data received from clients or servers.
 * It will be passed as an parameter to construct a
 * <code>ServerNetworkSystem</code> or a <code>ClientNetworkSystem</code>.
 *
 *
 * Created on 2014/9/17.
 *
 * @author Forrest Sun
 */
public interface NetworkMessageAdapter {
    void init(Server server);
    void init(Client client);
    void process(Connection connection, NetworkMessage message);

    void connected(Connection connection);

    void disconnected(Connection connection);
}
