package io.github.shaharpr.talkie.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.vdurmont.emoji.EmojiParser;

import io.github.shaharpr.talkie.client.TalkieTelegramBot;

public class TelegramUtils {
	public static void SendFastMessage(Update update, TalkieTelegramBot bot, String message) {
		SendMessage msg = SendMessage.builder().chatId(update.getMessage().getChatId())
				.text(EmojiParser.parseToUnicode(message)).build();
		try {
			bot.execute(msg);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("A Telegram error.", e);
		}
	}
}
