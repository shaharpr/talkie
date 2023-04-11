package io.github.shaharpr.talkie.talkiechat.backend.commands;

import io.github.shaharpr.talkie.talkiechat.backend.CommandHandler;
import io.github.shaharpr.talkie.utils.DateTime;

public class DateCommand {
	public static CommandHandler Command = (args) -> {
		return DateTime.getTimeNow();
	};
}
