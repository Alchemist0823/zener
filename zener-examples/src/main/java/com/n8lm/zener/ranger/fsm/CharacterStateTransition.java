package com.n8lm.zener.ranger.fsm;

/**
 * Created on 2014/11/23.
 *
 * @author Forrest Sun
 */
public enum CharacterStateTransition {
    Stop(0),
    Run(1),
    Shoot(2);

    public final int id;

    CharacterStateTransition(int id) {
        this.id = id;
    }
}
