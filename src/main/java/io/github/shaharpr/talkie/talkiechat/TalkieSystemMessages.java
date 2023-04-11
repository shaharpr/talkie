package io.github.shaharpr.talkie.talkiechat;

import com.theokanning.openai.completion.chat.ChatMessage;

/**
 * The System Instructions of Talkie
 * @author shaha
 *
 */
public class TalkieSystemMessages {
	// TODO: To add commands, first change the instructions
	public static final String TalkieInstructions = """
			You are Talkie, a helpful polite chatbot.
			To enrich your messages, You have access to all kinds of commands to help you formulate answers.
			The prefix of the commands is #
			The result of the command will sent to you in the next user message with this format: <last command> +|+ <command result>
			
			If you request a command, send ONLY the command without any another words!
			If there is no command that matches the user's request, use your knowledge to answer the user!
			
			A new conversation is started with a START_CONVERSATION message from the user.
			
			Commands available:
			#calculate - Calculate a math exercise - examples are - #calculate 2+2, #calculate 4*3
			#datetime - Get exactly what the day and time is now. - example is - #date
			
			The backend can add its own commands by appending the commands to these instructions.
			Here are the added commands:
			${commands}
			
			The backend can also add comments to these instructions
			The comments are: ${comments}
						""";
	
	public static String getTalkieInstructions(String commands, String comments) {
		String instructions = TalkieInstructions;
		instructions = instructions.replace("${commands}", commands != null ? commands : "There are no additional commands.");
		instructions = instructions.replace("${comments", comments != null ? comments : "There are no additional comments");
		return instructions;
	}
	
	public static final String PluginAddedPrefix = "We added a Plugin for you!";
	
	public static final ChatMessage[] TalkieChatExamples = {
			new ChatMessage("user", "Hello! Calculate 2+1"),
			new ChatMessage("assistant", "#calculate 2+1"),
			new ChatMessage("user", "#calculate 2+1 +|+ 3"),
			new ChatMessage("assistant", "The result is 3!")
	};
	
	public static final String RestartConversationMessage = "START_CONVERSATION";
	public static final String CommandOutputSeperator = " +|+ ";
}
