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
import com.n8lm.zener.input.InputListener;



/**
 * This mouse event is used to store the event generated in case the mouse wheel got moved.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public final class MouseEventWheelMoved extends AbstractMouseEvent {
  /**
   * The delta value that defines how much and into what direction the mouse wheel got moved.
   */
  private final int wheelDelta;

  /**
   * Create a new mouse wheel event and store the delta value.
   *
   * @param x     the x coordinate
   * @param y     the y coordinate
   * @param delta the delta of the mouse wheel movement
   */
  public MouseEventWheelMoved(final int x, final int y, final int delta) {
    super(x, y);
    wheelDelta = delta;
  }

  /**
   * Send the event to a Nifty input consumer.
   */
  @Override
  public boolean sendToNifty( final NiftyInputConsumer consumer) {
    return consumer.processMouseEvent(getX(), getY(), wheelDelta, -1, false);
  }

  /**
   * Send the event to a Slick input listener.
   */
  @Override
  public boolean sendToZener( final InputListener listener) {
    listener.mouseWheelMoved(wheelDelta);
    return true;
  }

}
