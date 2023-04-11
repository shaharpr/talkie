package io.github.shaharpr.talkie.talkiechat.backend;

import javax.script.ScriptException;

/**
 * A Talkie command handler function
 * @author shaha
 *
 */
public interface CommandHandler {
	String handler(String[] args) throws ScriptException;
}
