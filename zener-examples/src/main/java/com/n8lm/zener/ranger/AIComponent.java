package com.n8lm.zener.ranger;

import com.artemis.Component;
import com.n8lm.zener.ai.FSMSystem;

/**
 * Created on 2014/11/23.
 *
 * @author Forrest Sun
 */
public class AIComponent extends Component{
    private FSMSystem fsmSystem;

    public AIComponent() {
        fsmSystem = new FSMSystem();
        /*
        fsmSystem.addState(new IdleState());
        fsmSystem.addState(new RunState());
        fsmSystem.addState(new RunWithBowState());
        fsmSystem.addState(new ShootState());
        fsmSystem.addState(new BowState());*/
    }
}
