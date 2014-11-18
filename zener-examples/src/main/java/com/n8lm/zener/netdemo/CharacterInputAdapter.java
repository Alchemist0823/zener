package com.n8lm.zener.netdemo;

import com.artemis.World;
import com.n8lm.zener.input.InputAdapter;
import com.n8lm.zener.netdemo.messages.MoveMessage;
import com.n8lm.zener.network.ClientNetworkSystem;

/**
 * Created on 2014/11/2.
 *
 * @author Alchemist
 */
public class CharacterInputAdapter extends InputAdapter{

    private final World world;

    public CharacterInputAdapter(World world) {
        this.world = world;
    }

    @Override
    public void keyPressed(int key, char c) {
        switch (c) {
            case 'w':
            case 's':
            case 'd':
            case 'a':

                world.getSystem(ClientNetworkSystem.class).sendMessage(new MoveMessage(c));
                break;
        }
    }

}
