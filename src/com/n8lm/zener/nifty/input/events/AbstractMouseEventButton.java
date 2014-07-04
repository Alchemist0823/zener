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

/**
 * This is the basic mouse event for all mouse events that involve a mouse button. In addition to the normal abstract
 * mouse event this one is able to store the button that was used.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public abstract class AbstractMouseEventButton extends AbstractMouseEvent {
  /**
   * The ID of the button that was used.
   */
  private final int button;

  /**
   * Create the mouse event that involves a mouse button.
   *
   * @param x           the x coordinate of the event location
   * @param y           the y coordinate of the event location
   * @param mouseButton the mouse button that was used
   */
  protected AbstractMouseEventButton(final int x, final int y, final int mouseButton) {
    super(x, y);
    button = mouseButton;
  }

  /**
   * Get the ID of the button that was stored.
   *
   * @return the button ID
   */
  protected final int getButton() {
    return button;
  }
}
