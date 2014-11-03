/*
 * This file is part of Zener.
 *
 * Zener is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * Zener is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with Zener.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

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
