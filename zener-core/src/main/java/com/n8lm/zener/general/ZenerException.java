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

package com.n8lm.zener.general;

/**
 * A generic exception thrown by everything in the library
 * 
 * @author Alchemist
 */
public class ZenerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new exception with a detail message
	 * 
	 * @param message
	 *            The message describing the cause of this exception
	 */
	public ZenerException(String message) {
		super(message);
	}

	/**
	 * Create a new exception with a detail message
	 * 
	 * @param message
	 *            The message describing the cause of this exception
	 * @param e
	 *            The exception causing this exception to be thrown
	 */
	public ZenerException(String message, Throwable e) {
		super(message, e);
	}
}
