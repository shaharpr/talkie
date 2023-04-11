package io.github.shaharpr.talkie.client;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import io.github.shaharpr.talkie.utils.TelegramUtils;

public class TalkieTelegramBot extends TelegramLongPollingBot {
	public static final boolean TEST = true;
	public static Logger logger = LoggerFactory.getLogger(TalkieTelegramBot.class);
	
	public boolean connected = false;
	
	// Multi-User Support
	public HashMap<Long, String> OpenAIKeys;
	public HashMap<Long, String> currentConversationIDs;
	public TalkieRequests api;
	
	public Long currentChatId;
	public String currentConversationID;
	public String currentOpenAIKey;

	public TalkieTelegramBot(String botToken) {
		this(new DefaultBotOptions(), botToken);
	}

	public TalkieTelegramBot(DefaultBotOptions options, String botToken) {
		super(options, botToken);
		logger.info("Talkie is registered. Getting Retrofit Structure...");
		this.api = TalkieClient.getRequests(TalkieClientSettings.IntegratedServerURL);
		this.OpenAIKeys = new HashMap<>();
		this.currentConversationIDs = new HashMap<>();
		logger.info("Success. listening to updates...");
	}
	
	public void linkOpenAIKeyWithChatId(Long chatId, String key) {
		logger.info("New OpenAI Key registered!");
		OpenAIKeys.put(chatId, key);
	}
	
	public void linkConversationIDWithChatId(Long chatId, String cId) {
		logger.info("New Conversation ID registered!");
		currentConversationIDs.put(chatId, cId);
	}
	
	public String getOpenAIKeyWithChatId(Long chatId) {
		return OpenAIKeys.get(chatId);
	}
	
	public String getConversationIDWithChatId(Long chatId) {
		return currentConversationIDs.get(chatId);
	}

	@Override
	public void onUpdateReceived(Update update) {
		logger.info("Update Recieved! " + update);
		logger.info("Placing current user data in the variables...");
		
		if(update.hasMessage()) {
			currentChatId = update.getMessage().getChatId();
			currentConversationID = getConversationIDWithChatId(currentChatId);
			currentOpenAIKey = getOpenAIKeyWithChatId(currentChatId);
		}
		
		logger.info("Processing Update");
		
		// TODO Auto-generated method stub
		if (update.hasMessage() && update.getMessage().hasText()) {
			if (update.getMessage().getText().startsWith("/")) {
				try {
					TalkieTelegramCommands.AllCommands.get(update.getMessage().getText()).execute(update, this);
				} catch (NullPointerException e) {
					// no-op
				}
			}else {
				if(!connected) {
					TelegramUtils.SendFastMessage(update, this, "You are not authenticated!\nAuthenticate with /auth");
				}
				
				if(currentConversationIDs.get(update.getMessage().getChatId()) == null) {
					TelegramUtils.SendFastMessage(update, this, "?\nStart a new conversation please.");
				}
				try {
					logger.info("Sended Message to Server: " + update.getMessage().getText());
					
					TelegramUtils.SendFastMessage(update, this, TalkieClient.talkieGet(api, update.getMessage().getText(), OpenAIKeys.get(update.getMessage().getChatId()), currentConversationIDs.get(update.getMessage().getChatId())));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					TelegramUtils.SendFastMessage(update, this, "Oops! An error occoured! Try again later");
					throw new RuntimeException("A Talkie error.", e);
				}
			}
		}
	}

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "TalkieShaharprBot";
	}
}
