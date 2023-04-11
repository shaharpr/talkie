package io.github.shaharpr.talkie.client;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.vdurmont.emoji.EmojiParser;

import io.github.shaharpr.talkie.utils.TelegramUtils;

public class TalkieTelegramCommands {
	public static Logger logger = LoggerFactory.getLogger(TalkieTelegramCommands.class);
	
	public static Map<String, TelegramTalkieCommand> AllCommands = Map.ofEntries(
			Map.entry("/start", (update, bot) -> start(update, bot)),
			Map.entry("/auth", (update, bot) -> auth(update, bot)),
			Map.entry("/newConversation", (update, bot) -> newConversation(update, bot))
			);

	private static void start(Update update, TalkieTelegramBot bot) {
		logger.info("A new user has registered through the bot! " + update.getMessage().getFrom().getId() + "/" + update.getMessage().getFrom().getFirstName());
		SendMessage msg = SendMessage.builder().chatId(update.getMessage().getChatId())
				.text(EmojiParser.parseToUnicode("Welcome to the Talkie Telegram Bot! :blush:\n\n"
						+ "Talkie is a multipurpose chatbot that has a backend that helps it formulate answers. :incoming_envelope:\n"
						+ "This way the chatbot is more powerful and can access information in real time! For example Talkie know what time it is... :alarm_clock:\n\n"
						+ "In order to chat with Talkie, " + "first of all you need to connect "
						+ "to your OpenAI account with the /auth command."))
				.build();
		try {
			bot.execute(msg);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("A Telegram exception", e);
		}
	}

	private static void auth(Update update, TalkieTelegramBot bot) {
		TelegramUtils.SendFastMessage(update, bot, "Talkie uses the OpenAI API to process the message. :satellite_antenna:\n\n"
				+ "Basically, the OpenAI API is the heart of Talkie. :heart:\n"
				+ "Currently, Talkie does not have an OpenAI account management system :receipt:, so you need to connect directly with the API token. :key:\n\n"
				+ "Don't worry - your token is not saved, and is only used to connect to the API. :sunglasses:");
		if (TalkieTelegramBot.TEST) {
			TelegramUtils.SendFastMessage(update, bot, "BOTOWNER MODE: Authenticating using Bot's OpenAI Key...");
			bot.OpenAIKeys.put(update.getMessage().getChatId(), System.getenv("OPENAI_API_KEY"));
			bot.connected = true;
		}
		TelegramUtils.SendFastMessage(update, bot,
				"Authenticated! Now you can use " + "/newConversation to start a new conversation.");
	}

	private static void newConversation(Update update, TalkieTelegramBot bot) {
		logger.info("A user is starting or restarting the conversation...");
		var apiKey = bot.currentOpenAIKey;
		var currentConversationId = bot.currentConversationID;
		if (currentConversationId != null) {
			TelegramUtils.SendFastMessage(update, bot, "Restarting Conversation... :arrows_counterclockwise:");

			try {
				TalkieClient.deleteConversation(bot.api, apiKey, currentConversationId);
				logger.info("X " + currentConversationId + " X");
			} catch (Exception e) {
				TelegramUtils.SendFastMessage(update, bot, "Oops! A server-side error occoured! Try again later.");
				throw new RuntimeException("A server-side error occoured", e);
			}
		}
		try {
			var convId = TalkieClient.newConversation(bot.api, bot.currentOpenAIKey);
			logger.info(update.getMessage().getFrom().getFirstName() + " -> " + convId);
			bot.linkConversationIDWithChatId(bot.currentChatId, convId);
			TelegramUtils.SendFastMessage(update, bot, "Your Conversation ID is: " + convId);
			TelegramUtils.SendFastMessage(update, bot, "Created! Now every message you send will go to Talkie. Good luck!");
		} catch (Exception e) {
			TelegramUtils.SendFastMessage(update, bot, "Oops! A server-side error occoured! Try again later.");
			throw new RuntimeException("A server-side error occoured", e);
		}
	}
}
