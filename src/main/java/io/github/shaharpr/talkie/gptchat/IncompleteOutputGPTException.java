package io.github.shaharpr.talkie.gptchat;

/**
 * Throwed when the output of GPT-3.5 is too long.s
 * @author shaha
 *
 */
public class IncompleteOutputGPTException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7380872797889758151L;

	public IncompleteOutputGPTException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
