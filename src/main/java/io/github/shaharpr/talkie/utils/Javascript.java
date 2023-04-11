package io.github.shaharpr.talkie.utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * A JavaScript engine for Talkie backend based on Graal.js
 * @author shaha
 *
 */
public class Javascript {
	public static ScriptEngineManager EngineManager;
	public static ScriptEngine JavascriptEngine;
	
	/**
	 * Initialize the Javascript engine
	 */
	public static void InitializeJavascriptEngine() {
		EngineManager = new ScriptEngineManager();
		// We use the Graal.JS Javascript Engine instead of the internal one.
		System.setProperty("polyglot.engine.WarnInterpreterOnly", "false");
		JavascriptEngine = EngineManager.getEngineByName("graal.js");
	}
	
	/**
	 * Evaluate a Script on the engine
	 * @param script The script to execute
	 * @return A Java representation of the script returned data.
	 * @throws ScriptException
	 */
	public static Object Evaluate(String script) throws ScriptException {
		return JavascriptEngine.eval(script);
	}
}
