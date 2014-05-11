package com.n8lm.zener.nifty.input;

import com.n8lm.zener.input.InputListener;
import com.n8lm.zener.nifty.input.events.InputEvent;

/**
 * This is the input system that forwards all events to a Zener listener. Also this input system is so Zener, writing
 * Zener once is not even enough.
 *
 * @author Alchemsit
 */
public final class ZenerZenerInputSystem extends AbstractZenerInputSystem implements ForwardingInputSystem {
  /**
   * The input listener that will receive any events the NiftyGUI does not use.
   */
  private final InputListener listener;

  /**
   * Create the input system and set the listener that will receive any unused input events.
   *
   * @param targetListener the listener
   * @throws IllegalArgumentException in case the targetListener parameter is {@code null}
   */
  public ZenerZenerInputSystem(final InputListener targetListener) {
    if (targetListener == null) {
      throw new IllegalArgumentException("The target listener must not be NULL.");
    }
    listener = targetListener;
  }

  /**
   * Forward the input event to the Zener listener.
   */
  @Override
  protected void handleInputEvent(final InputEvent event) {
    event.sendToZener(listener);
  }

  @Override
  public void requestExclusiveMouse() {
    enableForwardingMode(ForwardingMode.mouse);
  }

  @Override
  public void requestExclusiveKeyboard() {
    enableForwardingMode(ForwardingMode.keyboard);
  }

  @Override
  public void requestExclusiveInput() {
    enableForwardingMode(ForwardingMode.all);
  }

  @Override
  public void releaseExclusiveMouse() {
    disableForwardingMode(ForwardingMode.mouse);
  }

  @Override
  public void releaseExclusiveKeyboard() {
    disableForwardingMode(ForwardingMode.keyboard);
  }

  @Override
  public void releaseExclusiveInput() {
    disableForwardingMode(ForwardingMode.all);
  }
}
