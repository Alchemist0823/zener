package com.n8lm.zener.app;

/**
 * Created on 2014/11/26.
 *
 * @author Forrest Sun
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The <code>AppStateManager</code> holds a list of {@link AppState}s which
 * it will update.<br/>
 * When an {@link AppState} is attached or detached, the
 * {@link AppState#attached(com.n8lm.zener.app.AppStateManager) } and
 * {@link AppState#detached(com.n8lm.zener.app.AppStateManager) } methods
 * will be called respectively.
 *
 * <p>The lifecycle for an attached AppState is as follows:</p>
 * <ul>
 * <li>attached() : called when the state is attached on the thread on which
 *                       the state was attached.
 * <li>initialize() : called ONCE on the render thread at the beginning of the next
 *                    AppStateManager.update().
 * <li>detached() : called when the state is detached on the thread on which
 *                       the state was detached.  This is not necessarily on the
 *                       render thread and it is not necessarily safe to modify
 *                       the scene graph, etc..
 * <li>cleanup() : called ONCE on the render thread at the beginning of the next update
 *                 after the state has been detached or when the application is
 *                 terminating.
 * </ul>
 *
 * @author Kirill Vainer, Paul Speed, Forrest Sun
 */
public class AppStateManager {

    /**
     *  Holds the active states once they are initialized.
     */
    private final List<AppState> initializing;
    private final List<AppState> states;
    private final List<AppState> terminating;

    private final BasicApp app;

    public AppStateManager(BasicApp app){
        initializing = new ArrayList<>();
        states = new ArrayList<>();
        terminating = new ArrayList<>();
        this.app = app;
    }

    /**
     *  Returns the BasicApp to which this AppStateManager belongs.
     */
    public BasicApp getBasicApp() {
        return app;
    }

    protected List<AppState> getStates(){
        return states;
    }

    /**
     * Attach a state to the AppStateManager, the same state cannot be attached
     * twice.
     *
     * @param state The state to attach
     * @return True if the state was successfully attached, false if the state
     * was already attached.
     */
    public boolean attach(AppState state){
            if (!states.contains(state) && !initializing.contains(state)){
                state.attached(this);
                initializing.add(state);
                return true;
            }else{
                return false;
            }
    }

    /**
     * Attaches many state to the AppStateManager in a way that is guaranteed
     * that they will all get initialized before any of their updates are run.
     * The same state cannot be attached twice and will be ignored.
     *
     * @param states The states to attach
     */
    public void attachAll(AppState... states){
        attachAll(Arrays.asList(states));
    }

    /**
     * Attaches many state to the AppStateManager in a way that is guaranteed
     * that they will all get initialized before any of their updates are run.
     * The same state cannot be attached twice and will be ignored.
     *
     * @param states The states to attach
     */
    public void attachAll(Iterable<AppState> states){
        for( AppState state : states ) {
            attach(state);
        }
    }

    /**
     * Detaches the state from the AppStateManager.
     *
     * @param state The state to detach
     * @return True if the state was detached successfully, false
     * if the state was not attached in the first place.
     */
    public boolean detach(AppState state){
        if (states.contains(state)){
            state.detached(this);
            states.remove(state);
            terminating.add(state);
            return true;
        } else if(initializing.contains(state)){
            state.detached(this);
            initializing.remove(state);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Check if a state is attached or not.
     *
     * @param state The state to check
     * @return True if the state is currently attached to this AppStateManager.
     *
     * @see AppStateManager#attach(com.n8lm.zener.app.AppState)
     */
    public boolean hasState(AppState state){
        return states.contains(state) || initializing.contains(state);
    }

    /**
     * Returns the first state that is an instance of subclass of the specified class.
     * @param <T>
     * @param stateClass
     * @return First attached state that is an instance of stateClass
     */
    public <T extends AppState> T getState(Class<T> stateClass){
        for (AppState state : getStates()) {
            if (stateClass.isAssignableFrom(state.getClass())){
                return (T) state;
            }
        }

        // This may be more trouble than its worth but I think
        // it's necessary for proper decoupling of states and provides
        // similar behavior to before where a state could be looked
        // up even if it wasn't initialized. -pspeed
        for (AppState state : initializing) {
            if (stateClass.isAssignableFrom(state.getClass())){
                return (T) state;
            }
        }
        return null;
    }

    protected void initializePending(){
        if (initializing.size() == 0)
            return;

        // Move the states that will be initialized
        // into the active array.  In all but one case the
        // order doesn't matter but if we do this here then
        // a state can detach itself in initialize().  If we
        // did it after then it couldn't.
        for (AppState state : initializing) {
            state.initialize(this, app);
        }

        states.addAll(initializing);
        initializing.clear();
    }

    protected void terminatePending(){
        if (terminating.size() == 0)
            return;

        for (AppState state : terminating) {
            state.cleanup();
        }

        // Remove just the states that were terminated...
        // which might now be a subset of the total terminating
        // list.
        terminating.clear();
    }

    /**
     * Calls update for attached states, do not call directly.
     * @param delta Time per frame.
     */
    public void update(int delta){

        // Cleanup any states pending
        terminatePending();

        // Initialize any states pending
        initializePending();

        // Update enabled states
        for (AppState state : getStates()){
            if (state.isEnabled()) {
                state.update(delta);
            }
        }
    }

    /**
     * Calls cleanup on attached states, do not call directly.
     */
    public void cleanup(){
        for (AppState state : states){
            state.cleanup();
        }
    }
}