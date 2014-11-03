package com.n8lm.zenertest.network.messages;

import com.n8lm.zener.network.NetworkMessage;

/**
 * Created on 2014/11/2.
 *
 * @author Alchemist
 */
public class MoveMessage extends NetworkMessage {
    public char ch;

    public MoveMessage() {}
    public MoveMessage(char c) {
        this.ch = c;
    }
}
