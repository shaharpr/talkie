package io.github.shaharpr.talkie.gptchat;

/**
 * Should not throw. Throwed in asynchronous GPT-3.5 calls
 * @author shaha
 *
 */
public class InProgressGPTException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8664148892834902186L;

	public InProgressGPTException() {
		// TODO Auto-generated constructor stub
	}

	public InProgressGPTException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InProgressGPTException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InProgressGPTException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InProgressGPTException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
