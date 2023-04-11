package io.github.shaharpr.talkie.talkiechat;

/**
 * Throwed when Talkie calls a command that doesn't exist
 * @author shaha
 *
 */
public class CommandNotImplementedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 885676569346774831L;

	public CommandNotImplementedException() {
		// TODO Auto-generated constructor stub
	}

	public CommandNotImplementedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CommandNotImplementedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public CommandNotImplementedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CommandNotImplementedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
