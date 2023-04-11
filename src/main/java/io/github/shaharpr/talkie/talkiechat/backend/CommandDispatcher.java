package io.github.shaharpr.talkie.talkiechat.backend;

import java.util.HashMap;
import java.util.List;

import javax.script.ScriptException;

import io.github.shaharpr.talkie.talkiechat.CommandNotImplementedException;
import io.github.shaharpr.talkie.talkiechat.backend.commands.*;

/**
 * This class executes and registers new commands
 * @author shaha
 *
 */
public class CommandDispatcher {
	/**
	 * The list of Commands available
	 */
	public static HashMap<String, CommandHandler> Commands = new HashMap<>();
	
	/**
	 * Register a new command in the chatbot
	 * @param commandName The name of the command, without #
	 * @param handler This function will be called every time the command is executed.
	 */
	public static void RegisterCommand(String commandName, CommandHandler handler) {
		Commands.put("#" + commandName, handler);
	}
	
	public static void RegisterCommands(List<CommandMetadata> metadatas) {
		for (CommandMetadata metadata : metadatas) {
			RegisterCommand(metadata.name, metadata.handler);
		}
	}
	
	/**
	 * Register the available commands.
	 */
	public static void InitializeCommands() {
		// TODO: third, Register your new commands HERE.
		RegisterCommand("calculate", CalculateCommand.Command);
		RegisterCommand("datetime", DateCommand.Command);
	}
	
	/**
	 * Execute a command by a name and arguments
	 * @param command the name of the command to execute, with #
	 * @param args the arguments of the command
	 * @return The command result, as a {@code String}
	 * @throws CommandNotImplementedException The requested command is not implemented
	 * @throws ScriptException there is a problem in the Javascript engine
	 */
	public static String ExecuteCommand(String command, String[] args) throws CommandNotImplementedException, ScriptException {
		var handler1 = Commands.get(command);
		if(handler1 == null) {
			throw new CommandNotImplementedException("Command " + command + " is not implemented!");
		}
		
		return handler1.handler(args);
	}
}
