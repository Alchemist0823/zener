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

package com.n8lm.zener.nifty.input.events;

import de.lessvoid.nifty.NiftyInputConsumer;
import com.n8lm.zener.nifty.input.InputState;



import com.n8lm.zener.input.InputListener;

/**
 * This mouse event is used to store the event generated in case a mouse button is pressed down.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public final class MouseEventPressed extends AbstractMouseEventButton {
  /**
   * Create a new mouse button pressed event.
   *
   * @param x           the x coordinate of the event location
   * @param y           the y coordinate of the event location
   * @param mouseButton the mouse button that was used
   */
  public MouseEventPressed(final int x, final int y, final int mouseButton) {
    super(x, y, mouseButton);
  }

  /**
   * Send the event to a Nifty input event consumer.
   */
  @Override
  public boolean sendToNifty( final NiftyInputConsumer consumer) {
    return consumer.processMouseEvent(getX(), getY(), 0, getButton(), true);
  }

  /**
   * Send the event to a slick input event consumer.
   */
  @Override
  public boolean sendToZener( final InputListener listener) {
    listener.mousePressed(getButton(), getX(), getY());
    return true;
  }

  /**
   * Tell the state to consume the next click event in case the click happened upon the GUI.
   */
  @Override
  public void updateState( final InputState state, final boolean handledByGUI) {
    state.setConsumeNextClick(handledByGUI);
  }
}
