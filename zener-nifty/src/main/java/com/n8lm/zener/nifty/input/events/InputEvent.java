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
import com.n8lm.zener.nifty.input.ForwardingMode;
import com.n8lm.zener.nifty.input.InputState;
import de.lessvoid.nifty.NiftyInputConsumer;


public interface InputEvent {
  /**
   * Check if the input event is supposed to be executed.
   *
   * @param state the input event state
   * @return {@code true} in case this event is supposed to be executed
   */
  boolean executeEvent(InputState state);

  /**
   * Check if this input event is forwarded.
   *
   * @param mode the current forwarding mode
   * @return {@code true} in case the event should be forwarded
   */
  boolean isForwarded(ForwardingMode mode);

  /**
   * Send the event to a nifty input consumer.
   *
   * @param consumer the consumer the event needs to be send to
   * @return {@code true} in case the event was handled by the consumer and must not be forwarded to any other event
   * handlers
   */
  boolean sendToNifty(NiftyInputConsumer consumer);

  /**
   * Send the event to a Zener input consumer.
   *
   * @param listener the input listener to receive this event
   * @return {@code true} in case the event was handled by the consumer and must not be forwarded to any other event
   * handlers
   */
  boolean sendToZener(InputListener listener);

  /**
   * Update the input event state.
   *
   * @param state        the input event state to update
   * @param handledByGUI {@code true} if this event was handled by the GUI
   */
  void updateState(InputState state, boolean handledByGUI);
}
