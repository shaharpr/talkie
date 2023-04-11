package io.github.shaharpr.talkie.gptchat;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.theokanning.openai.Usage;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

/**
 * This class is a wrapper around the OpenAI Chat API (Thanks to Theokanning for the API wrapper).
 * For easier GPT-3 development look at {@code SimplifiedGPTChat}
 * @see SimplifiedGPTChat
 * @author shaha
 *
 */
public class GPTChat {
	/**
	 * The OpenAI API Key. Create one at https://platform.openai.com/account/api-keys.
	 */
	public String OpenAIKey;
	protected OpenAiService OpenAIBackend;
	protected ChatCompletionRequest LatestRequest;
	protected ChatCompletionResult LatestResponse;
	/**
	 * The message history of the chat
	 */
	public ArrayList<ChatMessage> Messages;
	/**
	 * The instructions to instruct the chatbot
	 */
	public String SystemInstructions;
	/**
	 * The total tokens usage that you billed by
	 */
	public Usage TokensUsage;
	
	/**
	 * Create a new GPTChat
	 * @param key The OpenAI API Key
	 * @param systemMessage System Instructions to add to the request. leave null to disable
	 * system instructions
	 * @param timeout The time duration until the chat will throw
	 * a {@code SocketTimeoutException}
	 */
	public GPTChat(String key, String systemMessage, Duration timeout, GPTOptions.Options options) {
		this.OpenAIKey = key;
		this.SystemInstructions = systemMessage;
		this.Messages = new ArrayList<>();
		this.TokensUsage = new Usage();
		this.TokensUsage.setCompletionTokens(0);
		this.TokensUsage.setPromptTokens(0);
		this.TokensUsage.setTotalTokens(0);
		this.OpenAIBackend = new OpenAiService(key, timeout);
		this.LatestRequest = ChatCompletionRequest.builder()
				.maxTokens(options.MaxTokens)
				.model(options.Model)
				.temperature(options.Temperature)
				.build()
				;
		this.LatestResponse = null;
		this.LatestRequest.setMessages(Messages);
		if(systemMessage != null) this.Messages.add(new ChatMessage("system", systemMessage));
	}
	
	public GPTChat(String key, String systemMessage, Duration timeout) {
		this(key, systemMessage, timeout, new GPTOptions.DefaultTalkieOptions());
	}
	
	public GPTChat(String key, String systemMessage, Duration timeout, String model) {
		this(key, systemMessage, timeout, new GPTOptions.DefaultTalkieOptions(model));
	}
	
	/**
	 * Create a new Chat using an OpenAI key and Instructions
	 * @param key
	 * @param systemMessage
	 */
	public GPTChat(String key, String systemMessage) {
		this(key, systemMessage, Duration.ofSeconds(30));
	}
	
	/**
	 * Returns the last message sent (or received) by the class
	 * @return a ChatMessage
	 */
	public ChatMessage GetLatestMessage() {
		return this.Messages.get(this.Messages.size() - 1);
	}
	
	/**
	 * Add a ChatMessage to the Messages list.
	 * <b>Use {@code SendMessage} instead to send a message</b>
	 * @param message the ChatMessage to add
	 */
	public void AddMessage(ChatMessage message) {
		this.Messages.add(message);
	}
	
	/**
	 * Add a Message to the Messages list
	 * @param role Can be "assistant", or "user" - The role of the message sender
	 * @param message The message itself
	 */
	public void AddMessage(String role, String message) {
		this.AddMessage(new ChatMessage(role, message));
	}
	
	/**
	 * Add multiple messages to the message list
	 * @param messages Messages to add
	 */
	public void AddMessages(List<ChatMessage> messages) {
		this.Messages.addAll(messages);
	}
	
	/**
	 * Send the Message list to the API, and the GPT-3.5 will try to add an "assistant" message
	 * to respond to the user
	 * @param choiceNum Should be 0
	 * @throws Exception Unexpected finish reason
	 * @throws IncompleteOutputGPTException The GPT-3.5 generated too many tokens.
	 * The message returned is incomplete.
	 * @throws ContentFilterFlagGPTException The GPT-3.5 generated an output that violated
	 * the content policy of OpenAI.
	 * @throws InProgressGPTException Unexpected situation that the GPT-3.5 is still
	 * generating a message.
	 */
	protected void UpdateWithGPT(int choiceNum) throws Exception {
		this.LatestResponse = this.OpenAIBackend.createChatCompletion(LatestRequest);
		var choice = this.LatestResponse.getChoices().get(choiceNum);
		var finishReason = choice.getFinishReason();
		switch (finishReason) {
		case "stop":
			break;
		case "length":
			throw new IncompleteOutputGPTException("The Output that GPT-3.5 generated exceeded the max_tokens!");
		case "content_filter":
			throw new ContentFilterFlagGPTException("The output violating the OpenAI content policy!");
		case "null":
			throw new InProgressGPTException("The output is still generating...");
		default:
			throw new Exception("Unexpected Finish Reason - " + finishReason);
		}
		
		var message = choice.getMessage();
		this.AddMessage(message);
		this.TokensUsage.setCompletionTokens(this.LatestResponse.getUsage().getCompletionTokens() + this.LatestResponse.getUsage().getCompletionTokens());
		this.TokensUsage.setPromptTokens(this.LatestResponse.getUsage().getPromptTokens() + this.LatestResponse.getUsage().getPromptTokens());
		this.TokensUsage.setTotalTokens(this.LatestResponse.getUsage().getTotalTokens() + this.LatestResponse.getUsage().getTotalTokens());
	}
	
	/**
	 * Send the Message list to the API, and the GPT-3.5 will try to add an "assistant" message
	 * to respond to the user
	 * @throws Exception Unexpected finish reason
	 * @throws IncompleteOutputGPTException The GPT-3.5 generated too many tokens.
	 * The message returned is incomplete.
	 * @throws ContentFilterFlagGPTException The GPT-3.5 generated an output that violated
	 * the content policy of OpenAI.
	 * @throws InProgressGPTException Unexpected situation that the GPT-3.5 is still
	 * generating a message.
	 */
	public void UpdateWithGPT() throws Exception {
		this.UpdateWithGPT(0);
	}
	
	/**
	 * Send a message to the GPT-3.5 and add it to the message history. This will:
	 * <li>Add a User Message to the message history
	 * <li>Send it to the API and add the result to message history
	 * @param message The Message to send
	 * @return The message that the GPT-3.5 generated
	 * @throws Exception Unexpected finish reason
	 * @throws IncompleteOutputGPTException The GPT-3.5 generated too many tokens.
	 * The message returned is incomplete.
	 * @throws ContentFilterFlagGPTException The GPT-3.5 generated an output that violated
	 * the content policy of OpenAI.
	 * @throws InProgressGPTException Unexpected situation that the GPT-3.5 is still
	 * generating a message.
	 */
	public ChatMessage SendMessage(String message) throws Exception {
		this.AddMessage("user", message);
		this.UpdateWithGPT();
		return this.GetLatestMessage();
	}
}
