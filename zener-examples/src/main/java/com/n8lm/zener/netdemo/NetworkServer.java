package com.n8lm.zener.netdemo;

import com.artemis.World;
import com.n8lm.zener.app.BasicServer;
import com.n8lm.zener.app.ServerContainer;
import com.n8lm.zener.network.NetworkConfiguration;
import com.n8lm.zener.network.ServerNetworkSystem;
import com.n8lm.zener.general.ZenerException;

/**
 * Created on 2014/11/1.
 *
 * @author Alchemist
 */
public class NetworkServer extends BasicServer {

    @Override
    protected void init() {

        World world = new World("server");

        addWorld(world);

        NetworkConfiguration config = new MyNetworkConfiguration();
        world.setSystem(new CharacterSystem());
        world.setSystem(new ServerNetworkSystem(config, new ServerNetworkAdapter(world)));

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
