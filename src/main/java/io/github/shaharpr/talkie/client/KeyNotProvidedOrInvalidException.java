package io.github.shaharpr.talkie.client;

/**
 * Throwed when the OpenAI key provided is not correct, or not provided at all
 * @author shaha
 *
 */
public class KeyNotProvidedOrInvalidException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -354214157686869204L;

	public KeyNotProvidedOrInvalidException() {
		// TODO Auto-generated constructor stub
	}

	public KeyNotProvidedOrInvalidException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public KeyNotProvidedOrInvalidException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public KeyNotProvidedOrInvalidException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public KeyNotProvidedOrInvalidException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
