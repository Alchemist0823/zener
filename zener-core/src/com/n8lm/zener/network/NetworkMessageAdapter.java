package com.n8lm.zener.network;

import com.esotericsoftware.kryonet.Connection;

/**
 * Created on 2014/9/17.
 *
 * @author Alchemist
 */
public interface NetworkMessageAdapter {
    void process(Connection connection, NetworkMessage cast);
}
