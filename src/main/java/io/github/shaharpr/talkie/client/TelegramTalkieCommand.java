package io.github.shaharpr.talkie.client;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramTalkieCommand {
	void execute(Update currentUpdate, TalkieTelegramBot currentBot);
}
