package com.conflux.common;

public class ApplicationBaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ApplicationBaseException() {

	}

	/**
	 * @param message
	 */
	public ApplicationBaseException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public ApplicationBaseException(Throwable cause) {
		super(cause);

	}

	/**
	 * @param message
	 * @param cause
	 */
	public ApplicationBaseException(String message, Throwable cause) {
		super(message, cause);

	}

}
