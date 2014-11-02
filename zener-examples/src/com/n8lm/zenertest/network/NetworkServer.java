package com.n8lm.zenertest.network;

import com.artemis.World;
import com.esotericsoftware.kryonet.Connection;
import com.n8lm.zener.app.BasicServer;
import com.n8lm.zener.app.ServerContainer;
import com.n8lm.zener.network.NetworkConfiguration;
import com.n8lm.zener.network.NetworkMessage;
import com.n8lm.zener.network.NetworkMessageAdapter;
import com.n8lm.zener.network.ServerNetworkSystem;
import com.n8lm.zener.utils.ZenerException;

/**
 * Created on 2014/11/1.
 *
 * @author Alchemist
 */
public class NetworkServer extends BasicServer {

    @Override
    protected void init() {

        World world = new World();

        NetworkMessageAdapter networkAdapter = new NetworkMessageAdapter() {
            @Override
            public void process(Connection connection, NetworkMessage cast) {

            }
        };

        NetworkConfiguration config = new MyNetworkConfiguration();
        world.setSystem(new ServerNetworkSystem(config, networkAdapter));

        world.initialize();
    }

    @Override
    public void destory() {

    }


    public static void main(String[] args) throws ZenerException {
        NetworkServer server = new NetworkServer();
        ServerContainer container = new ServerContainer(server);
        container.start();
    }
}
