package com.n8lm.zener.ai;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2014/11/23.
 *
 * @author Forrest Sun
 */
public class FSMSystem {

    public Map<String, FSMState> stateMap;
    public FSMState currentState;

    public FSMSystem() {
        stateMap = new HashMap<>();
    }

    public void addState(FSMState state) {
        stateMap.put(state.getId(), state);

        if (stateMap.size() == 1)
            currentState = state;
    }

    public void performTransition(int t) throws ZenerAIException{
        String s = currentState.getNextState(t);
        if (s != null) {
            currentState = stateMap.get(s);
            if (currentState == null)
                throw new ZenerAIException("Next FSMState not in System");
        } else
            throw new ZenerAIException("No transition " + t + " in the state " + currentState.getId());
    }

}
