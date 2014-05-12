package com.n8lm.zener.utils;

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
