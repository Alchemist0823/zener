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
import com.n8lm.zener.nifty.input.InputState;
import de.lessvoid.nifty.NiftyInputConsumer;



/**
 * This mouse event is used to store the event generated in case a mouse button is clicked.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public final class MouseEventClicked extends AbstractMouseEventButton {
  /**
   * The count how often the button got clicked.
   */
  private final int count;

  /**
   * Create a new mouse button clicked event.
   *
   * @param x           the x coordinate of the event location
   * @param y           the y coordinate of the event location
   * @param mouseButton the mouse button that was used
   * @param clickCount  the count of times how often the button got clicked
   */
  public MouseEventClicked(final int x, final int y, final int mouseButton, final int clickCount) {
    super(x, y, mouseButton);
    count = clickCount;
  }

  /**
   * Reject this event in case the input state says so.
   */
  @Override
  public boolean executeEvent( final InputState state) {
    final boolean result = !state.isConsumeNextClick();
    state.setConsumeNextClick(false);
    return result;
  }

  /**
   * This would send the event to Nifty, how ever Nifty does not use such high-level events and so its never send to
   * Nifty.
   */
  @Override
  public boolean sendToNifty( final NiftyInputConsumer consumer) {
    return false;
  }

  /**
   * Send the event to a openal listener.
   */
  @Override
  public boolean sendToZener( final InputListener listener) {
    listener.mouseClicked(getButton(), getX(), getY(), count);
    return true;
  }
}
