package io.github.shaharpr.talkie.talkiechat.backend;

/**
 * Represents a pair of a command name and the handler function for the command
 * @author shaha
 *
 */
public class CommandMetadata {
	/**
	 * The name of the command
	 */
	public String name;
	/**
	 * The handler function of the command
	 */
	public CommandHandler handler;
	
	public CommandMetadata(String name, CommandHandler handler) {
		this.name = name;
		this.handler = handler;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("{(CommandMetadata) ");
		builder.append("#" + this.name);
		builder.append("}");
		return builder.toString();
	}
}
