package com.n8lm.zener.app;

/**
 * Created on 2014/11/26.
 *
 * @author Forrest Sun
 */
public interface AppState {

    void attached(AppStateManager appStateManager);

    void detached(AppStateManager appStateManager);

    /**
     * initialize the app state called in main loop
     * @param appStateManager the AppStateManager
     * @param app the BasicApp
     */
    void initialize(AppStateManager appStateManager, BasicApp app);

    /**
     * clean up the app state called in main loop
     */
    void cleanup();

    boolean isEnabled();

    void update(int delta);

}
