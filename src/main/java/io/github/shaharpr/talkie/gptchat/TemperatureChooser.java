package io.github.shaharpr.talkie.gptchat;

import java.time.Duration;
import java.util.Map;

/**
 * This class uses GPT-3.5 to Categorize an input message to Talkie, and suggest a sampling
 * temperature for the category.
 * @author shaha
 *
 */
public class TemperatureChooser {
	/**
	 * The instructions of the categorizer-GPT
	 */
	public static final String Instructions = """
			You are assistant that categorizes the user message to this categories:
			KNOWLEDGE_QUESTION
			QUESTION_ABOUT_YOU
			OTHER

			please send only the category.
						""";
	
	/**
	 * A conversion map from a category to a suggested sampling temperature
	 */
	public static final Map<String, Double> CategoryToTemp = Map.of(
			"OTHER", 0.7, // The default value is 0.7
			"KNOWLEDGE_QUESTION", 0.4, // be more stable in knowledge questions.
			"QUESTION_ABOUT_YOU", 0.8 // be more creative about Talkie
			);
	
	public static SimplifiedGPTChat Chat;
	
	public static String getCategory(String message, String openaiKey) throws Exception {
		SimplifiedGPTChat chat = Chat;
		return chat.SendSimplifiedMessage(message);
	}
	
	public static double getReccommendedTemp(String openaiKey, String message) throws Exception {
		return CategoryToTemp.get(getCategory(message, openaiKey));
	}
	
	public static void initTempChooser(String openaiKey) {
		Chat = new SimplifiedGPTChat(openaiKey, Instructions, Duration.ofSeconds(30), new GPTOptions.DefaultTalkieOptions(null, 0.5));
		Chat.AddMessage("user", "Hi, what's your name?");
		Chat.AddMessage("assistant", "QUESTION_ABOUT_YOU");
		// TODO: Which category is "I want to go shopping today. What I should take to the shopping?"
		Chat.AddMessage("user", "Hello! I want to go shopping today.");
		Chat.AddMessage("assistant", "OTHER");
		Chat.AddMessage("user", "What is GPT-3?");
		Chat.AddMessage("assistant", "KNOWLEDGE_QUESTION");
		Chat.AddMessage("user", "What time is it?");
		Chat.AddMessage("assistant", "KNOWLEDGE_QUESTION");
	}
}
