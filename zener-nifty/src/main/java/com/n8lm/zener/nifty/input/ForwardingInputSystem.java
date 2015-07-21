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
 * This interface is used in Input Systems that are able to forward events not handled by Nifty-GUI to other input
 * listeners.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public interface ForwardingInputSystem {
  /**
   * Request exclusive mouse access. This causes that mouse events are not send to the Nifty-GUI but to the underlying
   * game directly.
   * <p/>
   * <b>Important:</b> Be sure to disable this mode again as it renders the Nifty-GUI non-functional. While enabled its
   * impossible to interact with the GUI elements by mouse.
   */
  void requestExclusiveMouse();

  /**
   * Request exclusive keyboard access. This causes that keyboard events are not send to the Nifty-GUI but to the
   * underlying game directly.
   * <p/>
   * <b>Important:</b> Be sure to disable this mode again as it renders the Nifty-GUI non-functional. While enabled its
   * impossible to interact with the GUI elements by keyboard.
   */
  void requestExclusiveKeyboard();

  /**
   * Request exclusive input access. This causes that mouse and keyboard events are not send to the Nifty-GUI but to
   * the
   * underlying game directly.
   * <p/>
   * <b>Important:</b> Be sure to disable this mode again as it renders the Nifty-GUI non-functional. While enabled its
   * impossible to interact with the GUI elements at all.
   */
  void requestExclusiveInput();

  /**
   * This command releases the exclusive access on the mouse events. After this command is called, the input events are
   * send to the Nifty-GUI again.
   */
  void releaseExclusiveMouse();

  /**
   * This command releases the exclusive access on the keyboard events. After this command is called, the input events
   * are send to the Nifty-GUI again.
   */
  void releaseExclusiveKeyboard();

  /**
   * Release all exclusive access requests. This resets in input system to its normal operation mode. All input events
   * are first send to the Nifty-GUI and second to an additional listener if any.
   */
  void releaseExclusiveInput();
}
