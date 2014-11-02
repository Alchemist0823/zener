package com.n8lm.zenertest.network;

import com.n8lm.zener.network.NetworkConfiguration;

/**
 * Created on 2014/11/2.
 *
 * @author Alchemist
 */
public class MyNetworkConfiguration extends NetworkConfiguration {
    @Override
    protected void addMessageTypes() {
        addMessage(LoginMessage.class);
    }

    @Override
    public int getServerPort() {
        return 0;
    }
}
