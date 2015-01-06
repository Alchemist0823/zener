package com.n8lm.zener.app;

/**
 * Created on 2014/11/26.
 *
 * @author Forrest Sun
 */
public interface AppState {

    public void attached(AppStateManager appStateManager);

    public void detached(AppStateManager appStateManager);

    /**
     * initialize the app state called in main loop
     * @param appStateManager
     * @param app
     */
    public void init(AppStateManager appStateManager, BasicApp app);

    /**
     * clean up the app state called in main loop
     */
    public void cleanup();

    public boolean isEnabled();

    public void update(int delta);

}
