package com.n8lm.zener.ai;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2014/11/23.
 *
 * @author Forrest Sun
 */
public abstract class FSMState {
    final Map<Integer, String> map;

    public FSMState() {
        map = new HashMap<>();
    }

    public abstract String getId();

    public String getNextState(int t) {
        return map.get(t);
    }
}
