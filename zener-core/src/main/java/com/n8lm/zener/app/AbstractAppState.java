package com.n8lm.zener.app;

/**
 * Created on 2014/11/26.
 *
 * @author Forrest Sun
 */
public class AbstractAppState implements AppState {


    boolean isEnabled = true;

    public void setEnable(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }


    @Override
    public void attached(AppStateManager appStateManager) {

    }

    @Override
    public void detached(AppStateManager appStateManager) {

    }

    @Override
    public void init(AppStateManager appStateManager, BasicApp app) {

    }

    @Override
    public void cleanup() {

    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void update(int delta) {

    }
}
