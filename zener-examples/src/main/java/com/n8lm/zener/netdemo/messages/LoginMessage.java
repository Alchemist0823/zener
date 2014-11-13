package com.n8lm.zener.netdemo.messages;

import com.n8lm.zener.network.NetworkMessage;

/**
 * Created on 2014/11/2.
 *
 * @author Alchemist
 */
public class LoginMessage extends NetworkMessage{
    public String name;

    public LoginMessage() {}
    public LoginMessage(String name) {
        this.name = name;
    }
}
