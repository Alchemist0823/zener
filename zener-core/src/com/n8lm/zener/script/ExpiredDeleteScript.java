package com.n8lm.zener.script;

import com.artemis.World;

/**
 * Created on 2014/9/12.
 *
 * @author Alchemist
 */
public class ExpiredDeleteScript implements NativeScript{
    @Override
    public void run(World world, Event event) {
        if (event.getType() == Event.DELAYED_END)
            world.deleteEntity(event.getDispatcher());
    }
}
