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

/**
 * This small class is able to store some states that are needed for the communication between different input events.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public final class InputState {
  /**
   * In case this state is switched to {@code true} the next click events needs to be consumed entirely.
   */
  private boolean consumeNextClick;

  /**
   * Check if the next click event needs to be consumed.
   *
   * @return {@code true} if the next click event needs to be consumed
   */
  public boolean isConsumeNextClick() {
    return consumeNextClick;
  }

  /**
   * Set the consume next input event flag.
   *
   * @param value {@code true} in case the next input event is supposed to be consumed
   */
  public void setConsumeNextClick(final boolean value) {
    consumeNextClick = value;
  }
}
