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
 * Describes classes capable of responding to key presses
 * 
 * @author kevin
 */
public interface KeyListener extends ControlledInputReciever {
	/**
	 * Notification that a key was pressed
	 * 
	 * @param key The key code that was pressed (@see com.n8lm.zener..Input)
	 * @param c The character of the key that was pressed
	 */
	public abstract void keyPressed(int key, char c);

	/**
	 * Notification that a key was released
	 * 
	 * @param key The key code that was released (@see com.n8lm.zener..Input)
	 * @param c The character of the key that was released
	 */
	public abstract void keyReleased(int key, char c);

}