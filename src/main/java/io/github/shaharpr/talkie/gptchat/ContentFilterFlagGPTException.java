package io.github.shaharpr.talkie.gptchat;

/**
 * Throwed when the GPT-3.5 is generating an illegal content
 * @author shaha
 *
 */
public class ContentFilterFlagGPTException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2962108288346069959L;

	public ContentFilterFlagGPTException() {
		// TODO Auto-generated constructor stub
	}

	public ContentFilterFlagGPTException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ContentFilterFlagGPTException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ContentFilterFlagGPTException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ContentFilterFlagGPTException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
