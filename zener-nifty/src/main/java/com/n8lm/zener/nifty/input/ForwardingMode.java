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
 * This enumerator stores the forwarding modes the input systems are supporting.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public enum ForwardingMode {
  /**
   * Normal mode. Relay all events first to the Nifty-GUI and in case they are not handled by the GUI, forward them to
   * the forwarding listener.
   */
  none,

  /**
   * Forward all mouse events directly to the second listeners and do not send them to the Nifty-GUI.
   */
  mouse,

  /**
   * Forward all keyboard events directly to the second listeners and do not send them to the Nifty-GUI.
   */
  keyboard,

  /**
   * Forward all input events (mouse and keyboard) directly to the second listeners and do not send them to the
   * Nifty-GUI.
   */
  all
}
