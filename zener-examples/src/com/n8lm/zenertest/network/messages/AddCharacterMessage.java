package com.n8lm.zenertest.network.messages;

import com.n8lm.zener.network.NetworkMessage;
import com.n8lm.zenertest.network.CharacterComponent;

/**
 * Created on 2014/11/2.
 *
 * @author Alchemist
 */
public class AddCharacterMessage extends NetworkMessage{
    public String name;
    public int id;
    public float x, y;

    public AddCharacterMessage() {}

    public AddCharacterMessage(String name, int id, float x, float y) {
        this.name = name;
        this.id = id;
        this.x = x;
        this.y = y;
    }
}
