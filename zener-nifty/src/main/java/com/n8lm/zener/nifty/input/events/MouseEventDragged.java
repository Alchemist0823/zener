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

import com.n8lm.zener.input.InputListener;
import de.lessvoid.nifty.NiftyInputConsumer;

/**
 * This mouse event is used to store the event generated in case the mouse cursor is moved with one button pressed.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public final class MouseEventDragged extends AbstractMouseEventButton {
  /**
   * The X coordinate where the movement stopped.
   */
  private final int targetX;

  /**
   * The Y coordinate where the movement stopped.
   */
  private final int targetY;

  /**
   * Create a mouse dragged event.
   *
   * @param mouseButton the mouse button that was used
   * @param startX      the X coordinate of the location where the movement started
   * @param startY      the Y coordinate of the location where the movement started
   * @param endX        the X coordinate of the location where the movement stopped
   * @param endY        the Y coordinate of the location where the movement stopped
   */
  public MouseEventDragged(final int mouseButton, final int startX, final int startY, final int endX, final int endY) {
    super(startX, startY, mouseButton);
    targetX = endX;
    targetY = endY;
  }

  /**
   * Send the event to the Nifty input consumer.
   */
  @Override
  public boolean sendToNifty( final NiftyInputConsumer consumer) {
    return consumer.processMouseEvent(targetX, targetY, 0, getButton(), true);
  }

  /**
   * Send the event to the Slick input listener.
   */
  @Override
  public boolean sendToZener( final InputListener listener) {
    listener.mouseDragged(getX(), getY(), targetX, targetY);
    return true;
  }

}
