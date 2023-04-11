package io.github.shaharpr.talkie.talkiechat.backend.commands;

import io.github.shaharpr.talkie.talkiechat.backend.CommandHandler;
import io.github.shaharpr.talkie.utils.Javascript;

/**
 * Use this command to calculate equations
 * @author shaha
 *
 */
public class CalculateCommand {
	public static CommandHandler Command = (args) -> {
		Javascript.InitializeJavascriptEngine();
		var num = (Integer) Javascript.Evaluate(args[0]);
		return num.toString();
	};
}
