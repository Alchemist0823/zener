package com.n8lm.zenertest.network.messages;

import com.n8lm.zener.network.NetworkMessage;
import com.n8lm.zenertest.network.CharacterComponent;

/**
 * Created on 2014/11/2.
 *
 * @author Alchemist
 */
public class UpdateCharacterMessage extends NetworkMessage{
    public int id;
    public float x, y;

    public UpdateCharacterMessage() {}

    public UpdateCharacterMessage(CharacterComponent cc) {
        this.id = cc.getId();
        this.x = cc.getX();
        this.y = cc.getY();
    }
}
