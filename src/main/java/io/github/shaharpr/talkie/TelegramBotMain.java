package io.github.shaharpr.talkie;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import io.github.shaharpr.talkie.client.TalkieTelegramBot;

public class TelegramBotMain {

	public static void main(String[] args) throws TelegramApiException {
		// TODO Auto-generated method stub
		System.out.println("Starting Telegram Bot...");
		
		TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
		api.registerBot(new TalkieTelegramBot(System.getenv("TALKIE_BOT_TOKEN")));
	}

}
