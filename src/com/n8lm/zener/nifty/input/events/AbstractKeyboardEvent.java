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

import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;

import com.n8lm.zener.nifty.input.ForwardingMode;
import com.n8lm.zener.nifty.input.InputState;

/**
 * This is the abstract keyboard event that stores the data all keyboard events got in common.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
@SuppressWarnings("AbstractClassExtendsConcreteClass")
public abstract class AbstractKeyboardEvent extends KeyboardInputEvent implements InputEvent {
  /**
   * Create the keyboard event and store the ID of the key and the character.
   *
   * @param keyId       the ID of the key that was used
   * @param keyChar     the character assigned to the used key
   * @param keyDown     {@code true} in case the key is pressed down
   * @param shiftDown   {@code true} in case shift is pressed down at the same time
   * @param controlDown {@code true} in case control is pressed down at the same time
   */
  protected AbstractKeyboardEvent(
      final int keyId, final char keyChar, final boolean keyDown, final boolean shiftDown, final boolean controlDown) {
    super(keyId, keyChar, keyDown, shiftDown, controlDown);
  }

  /**
   * {@inheritDoc} Default implementation allows this event to be executed in any case.
   */
  @Override
  public boolean executeEvent(final InputState state) {
    return true;
  }

  @Override
  public boolean isForwarded(final ForwardingMode mode) {
    return (mode == ForwardingMode.keyboard) || (mode == ForwardingMode.all);
  }

  /**
   * {@inheritDoc} The default implementation never updates the state.
   */
  @Override
  public void updateState(final InputState state, final boolean handledByGUI) {
    // nothing to do
  }
}
