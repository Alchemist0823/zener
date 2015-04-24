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

package com.n8lm.zener.input;

/**
 * Description of classes capable of responding to controller events
 * 
 * @author kevin
 */
public interface ControllerListener extends ControlledInputReceiver {

	/**
	 * Notification that a button control has been pressed on 
	 * the controller.
	 * 
	 * @param controller The index of the controller on which the control
	 * was pressed.
	 * @param button The index of the button pressed (starting at 1)
	 */
	public abstract void controllerButtonPressed(int controller, int button);

	/**
	 * Notification that a button control has been released on 
	 * the controller.
	 * 
	 * @param controller The index of the controller on which the control
	 * was released.
	 * @param button The index of the button released (starting at 1)
	 */
	public abstract void controllerButtonReleased(int controller, int button);

}