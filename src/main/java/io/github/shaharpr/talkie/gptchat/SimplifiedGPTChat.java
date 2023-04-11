package io.github.shaharpr.talkie.gptchat;

import java.time.Duration;

/**
 * A simplified version of GPTChat. Use this class to send messages to GPT-3.5
 * @author shaha
 *
 */
public class SimplifiedGPTChat extends GPTChat {

	public SimplifiedGPTChat(String key, String systemMessage, Duration timeout, GPTOptions.Options options) {
		super(key, systemMessage, timeout, options);
		// TODO Auto-generated constructor stub
	}
	
	public SimplifiedGPTChat(String key, String systemMessage, Duration timeout) {
		this(key, systemMessage, timeout, new GPTOptions.DefaultTalkieOptions());
	}

	public SimplifiedGPTChat(String key, String systemMessage) {
		super(key, systemMessage);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Send a Message to GPT-3.5 and get a response
	 * @param message The message to send
	 * @return The message returned from GPT-3.5, as a {@code String}
	 * @throws Exception Unexpected finish reason
	 * @throws IncompleteOutputGPTException The GPT-3.5 generated too many tokens.
	 * The message returned is incomplete.
	 * @throws ContentFilterFlagGPTException The GPT-3.5 generated an output that violated
	 * the content policy of OpenAI.
	 * @throws InProgressGPTException Unexpected situation that the GPT-3.5 is still
	 * generating a message.
	 */
	public String SendSimplifiedMessage(String message) throws Exception {
		return this.SendMessage(message).getContent();
	}

}
