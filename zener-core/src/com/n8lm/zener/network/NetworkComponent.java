package com.n8lm.zener.network;

import com.artemis.Component;

/**
 * Created on 2014/9/17.
 *
 * @author Alchemist
 */
public class NetworkComponent extends Component{
    private int networkId;

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }
}
