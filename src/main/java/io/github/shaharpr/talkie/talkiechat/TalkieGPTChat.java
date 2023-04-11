package io.github.shaharpr.talkie.talkiechat;

import java.time.Duration;
import java.util.Arrays;

import javax.script.ScriptException;

import io.github.shaharpr.talkie.gptchat.ContentFilterFlagGPTException;
import io.github.shaharpr.talkie.gptchat.GPTOptions;
import io.github.shaharpr.talkie.gptchat.InProgressGPTException;
import io.github.shaharpr.talkie.gptchat.IncompleteOutputGPTException;
import io.github.shaharpr.talkie.gptchat.SimplifiedGPTChat;
import io.github.shaharpr.talkie.talkiechat.backend.CommandDispatcher;

/**
 * The implementation of Talkie on the GPT-3.5 platform.
 * @author shaha
 *
 */
public class TalkieGPTChat extends SimplifiedGPTChat {
	/**
	 * Create a new Talkie Chat.
	 * @param key The OpenAI Key
	 * @param timeout The duration until a Timeout
	 */
	public TalkieGPTChat(String key, Duration timeout, GPTOptions.Options options) {
		// TODO Add Talkie Commands and Comments
		super(key, TalkieSystemMessages.getTalkieInstructions(null, null), timeout, options);
		this.AddMessages(Arrays.asList(TalkieSystemMessages.TalkieChatExamples));
		this.RestartConversation();
		CommandDispatcher.InitializeCommands();
		// TODO Auto-generated constructor stub
	}
	
	public TalkieGPTChat(String key, GPTOptions.Options options) {
		this(key, Duration.ofSeconds(30), options);
	}
	
	public TalkieGPTChat(String key, Duration timeout) {
		this(key, timeout, new GPTOptions.DefaultTalkieOptions());
	}
	
	/**
	 * Create a new Talkie Chat with a Timeout of 30 seconds.
	 * @param key The OpenAI Key to use
	 */
	public TalkieGPTChat(String key) {
		this(key, new GPTOptions.DefaultTalkieOptions());
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Restart the Conversation. This will add a {@code RestartConversationMessage} to
	 * the message history
	 */
	public void RestartConversation() {
		this.AddMessage("user", TalkieSystemMessages.RestartConversationMessage);
	}
	
	/**
	 * Call the backend Command dispatcher
	 * @param command
	 * @param arguments
	 * @return
	 * @throws ScriptException
	 * @throws CommandNotImplementedException
	 */
	protected String CallBackend(String command, String[] arguments) throws ScriptException, CommandNotImplementedException {
		String result = "";
		
		result = CommandDispatcher.ExecuteCommand(command, arguments);
		
		return result;
	}
	
	/**
	 * Convert a command output to text with GPT-3.5
	 * @param output The output of the command
	 * @return
	 * @throws Exception
	 */
	protected String GetTextFromCommand(String output) throws Exception {
		var args = output.split(" ");
		var command = args[0];
		var splittedArgs = Arrays.copyOfRange(args, 1, args.length);
		var backendOutput = this.CallBackend(command, splittedArgs);
		return this.SendSimplifiedMessage(backendOutput + TalkieSystemMessages.CommandOutputSeperator + backendOutput);
	}
	
	/**
	 * Send an advanced Talkie message. This function will extract the commands from the
	 * output of Talkie and process them.
	 * @param message The message to Send.
	 * @return a Message from Talkie, processed with commands
	 * @throws Exception Unexpected finish reason
	 * @throws IncompleteOutputGPTException The GPT-3.5 generated too many tokens.
	 * The message returned is incomplete.
	 * @throws ContentFilterFlagGPTException The GPT-3.5 generated an output that violated
	 * the content policy of OpenAI.
	 * @throws InProgressGPTException Unexpected situation that the GPT-3.5 is still
	 * generating a message.
	 * @throws CommandNotImplementedException The GPT-3.5 called a function that doesn't exist
	 * @throws ScriptException - An exception on the backend Javascript engine
	 */
	public String SendSimplifiedMessageAndCallBackend(String message) throws Exception {
		String output = this.SendSimplifiedMessage(message);
		String calculatedOutput = output;
		if(output.startsWith("#")) {
			calculatedOutput = this.GetTextFromCommand(output);
		}
		
		return calculatedOutput;
	}
}
