package com.n8lm.zener.input;

import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created on 2015/4/23.
 *
 * @author Forrest Sun
 */
public class InputManager implements InputListener {

    /**
     * True if the event has been consumed
     */
    protected boolean consumed = false;

    /**
     * Notification from an event handle that an event has been consumed
     */
    public void consumeEvent() {
        consumed = true;
    }

    /**
     * A list of listeners to be notified of input events
     */
    protected HashSet<ControlledInputReceiver> allListeners = new HashSet<>();
    /**
     * The listeners to notify of key events
     */
    protected ArrayList<KeyListener> keyListeners = new ArrayList<>();
    /**
     * The listener to add
     */
    protected ArrayList<KeyListener> keyListenersToAdd = new ArrayList<>();
    /**
     * The listeners to notify of mouse events
     */
    protected ArrayList<MouseListener> mouseListeners = new ArrayList<>();
    /**
     * The listener to add
     */
    protected ArrayList<MouseListener> mouseListenersToAdd = new ArrayList<>();
    /**
     * The listener to nofiy of controller events
     */
    protected ArrayList<ControllerListener> controllerListeners = new ArrayList<>();


    /**
     * Add a listener to be notified of input events
     *
     * @param listener The listener to be notified
     */
    public void addListener(InputListener listener) {
        addKeyListener(listener);
        addMouseListener(listener);
        addControllerListener(listener);
    }

    /**
     * Add a key listener to be notified of key input events
     *
     * @param listener The listener to be notified
     */
    public void addKeyListener(KeyListener listener) {
        keyListenersToAdd.add(listener);
    }

    /**
     * Add a key listener to be notified of key input events
     *
     * @param listener The listener to be notified
     */
    private void addKeyListenerImpl(KeyListener listener) {
        if (keyListeners.contains(listener)) {
            return;
        }
        keyListeners.add(listener);
        allListeners.add(listener);
    }

    /**
     * Add a mouse listener to be notified of mouse input events
     *
     * @param listener The listener to be notified
     */
    public void addMouseListener(MouseListener listener) {
        mouseListenersToAdd.add(listener);
    }

    /**
     * Add a mouse listener to be notified of mouse input events
     *
     * @param listener The listener to be notified
     */
    private void addMouseListenerImpl(MouseListener listener) {
        if (mouseListeners.contains(listener)) {
            return;
        }
        mouseListeners.add(listener);
        allListeners.add(listener);
    }

    /**
     * Add a controller listener to be notified of controller input events
     *
     * @param listener The listener to be notified
     */
    public void addControllerListener(ControllerListener listener) {
        if (controllerListeners.contains(listener)) {
            return;
        }
        controllerListeners.add(listener);
        allListeners.add(listener);
    }

    /**
     * Remove all the listeners from this input
     */
    public void removeAllListeners() {
        removeAllKeyListeners();
        removeAllMouseListeners();
        removeAllControllerListeners();
    }

    /**
     * Remove all the key listeners from this input
     */
    public void removeAllKeyListeners() {
        allListeners.removeAll(keyListeners);
        keyListeners.clear();
    }

    /**
     * Remove all the mouse listeners from this input
     */
    public void removeAllMouseListeners() {
        allListeners.removeAll(mouseListeners);
        mouseListeners.clear();
    }

    /**
     * Remove all the controller listeners from this input
     */
    public void removeAllControllerListeners() {
        allListeners.removeAll(controllerListeners);
        controllerListeners.clear();
    }

    /**
     * Add a listener to be notified of input events. This listener
     * will get events before others that are currently registered
     *
     * @param listener The listener to be notified
     */
    public void addPrimaryListener(InputListener listener) {
        removeListener(listener);

        keyListeners.add(0, listener);
        mouseListeners.add(0, listener);
        controllerListeners.add(0, listener);

        allListeners.add(listener);
    }

    /**
     * Remove a listener that will no longer be notified
     *
     * @param listener The listen to be removed
     */
    public void removeListener(InputListener listener) {
        removeKeyListener(listener);
        removeMouseListener(listener);
        removeControllerListener(listener);
    }

    /**
     * Remove a key listener that will no longer be notified
     *
     * @param listener The listen to be removed
     */
    public void removeKeyListener(KeyListener listener) {
        keyListeners.remove(listener);

        if (!mouseListeners.contains(listener) && !controllerListeners.contains(listener)) {
            allListeners.remove(listener);
        }
    }

    /**
     * Remove a controller listener that will no longer be notified
     *
     * @param listener The listen to be removed
     */
    public void removeControllerListener(ControllerListener listener) {
        controllerListeners.remove(listener);

        if (!mouseListeners.contains(listener) && !keyListeners.contains(listener)) {
            allListeners.remove(listener);
        }
    }

    /**
     * Remove a mouse listener that will no longer be notified
     *
     * @param listener The listen to be removed
     */
    public void removeMouseListener(MouseListener listener) {
        mouseListeners.remove(listener);

        if (!controllerListeners.contains(listener) && !keyListeners.contains(listener)) {
            allListeners.remove(listener);
        }
    }


    @Override
    public void controllerButtonPressed(int controller, int button) {
        consumed = false;
        for (int i = 0; i < controllerListeners.size(); i++) {
            ControllerListener listener = controllerListeners.get(i);
            if (listener.isAcceptingInput()) {
                listener.controllerButtonPressed(controller, button);
                if (consumed) {
                    break;
                }
            }
        }
    }

    @Override
    public void controllerButtonReleased(int controller, int button) {

        consumed = false;
        for (int i = 0; i < controllerListeners.size(); i++) {
            ControllerListener listener = controllerListeners.get(i);
            if (listener.isAcceptingInput()) {
                // assume button release
                listener.controllerButtonReleased(controller, button);
                if (consumed) {
                    break;
                }
            }
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        consumed = false;
        for (int j = 0; j < keyListeners.size(); j++) {
            KeyListener listener = keyListeners.get(j);
            if (listener.isAcceptingInput()) {
                listener.keyPressed(key, c);
                if (consumed) {
                    break;
                }
            }
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        consumed = false;
        for (int j = 0; j < keyListeners.size(); j++) {
            KeyListener listener = keyListeners.get(j);
            if (listener.isAcceptingInput()) {
                listener.keyReleased(key, c);
                if (consumed) {
                    break;
                }
            }
        }
    }

    @Override
    public void mouseWheelMoved(int change) {
        consumed = false;
        for (int i = 0; i < mouseListeners.size(); i++) {
            MouseListener listener = mouseListeners.get(i);
            if (listener.isAcceptingInput()) {
                listener.mouseWheelMoved(change);
                if (consumed) {
                    break;
                }
            }
        }
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        consumed = false;
        for (int i = 0; i < mouseListeners.size(); i++) {
            MouseListener listener = mouseListeners.get(i);
            if (listener.isAcceptingInput()) {
                listener.mouseClicked(button, x, y, clickCount);
                if (consumed) {
                    break;
                }
            }
        }
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        consumed = false;
        for (int i = 0; i < mouseListeners.size(); i++) {
            MouseListener listener = mouseListeners.get(i);
            if (listener.isAcceptingInput()) {
                listener.mousePressed(Mouse.getEventButton(), x, y);
                if (consumed) {
                    break;
                }
            }
        }
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        consumed = false;
        for (int i = 0; i < mouseListeners.size(); i++) {
            MouseListener listener = mouseListeners.get(i);
            if (listener.isAcceptingInput()) {
                listener.mouseReleased(Mouse.getEventButton(), x, y);
                if (consumed) {
                    break;
                }
            }
        }
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        consumed = false;
        for (int i = 0; i < mouseListeners.size(); i++) {
            MouseListener listener = mouseListeners.get(i);
            if (listener.isAcceptingInput()) {
                listener.mouseMoved(oldx, oldy, newx, newy);
                if (consumed) {
                    break;
                }
            }
        }
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        consumed = false;
        for (int i = 0; i < mouseListeners.size(); i++) {
            MouseListener listener = mouseListeners.get(i);
            if (listener.isAcceptingInput()) {
                listener.mouseDragged(oldx, oldy, newx, newy);
                if (consumed) {
                    break;
                }
            }
        }
    }

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {
        Iterator<ControlledInputReceiver> all = allListeners.iterator();
        while (all.hasNext()) {
            ControlledInputReceiver listener = all.next();
            listener.inputEnded();
        }
    }

    @Override
    public void inputStarted() {
        // add any listeners requested since last time
        for (int i = 0; i < keyListenersToAdd.size(); i++) {
            addKeyListenerImpl(keyListenersToAdd.get(i));
        }
        keyListenersToAdd.clear();
        for (int i = 0; i < mouseListenersToAdd.size(); i++) {
            addMouseListenerImpl(mouseListenersToAdd.get(i));
        }
        mouseListenersToAdd.clear();

        // notify inputStarted
        Iterator<ControlledInputReceiver> allStarts = allListeners.iterator();
        while (allStarts.hasNext()) {
            ControlledInputReceiver listener = allStarts.next();
            listener.inputStarted();
        }
    }
}
