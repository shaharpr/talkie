package io.github.shaharpr.talkie.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import io.github.shaharpr.talkie.Main;

/**
 * A settings auto-configurator for the java client
 * @author shaha
 *
 */
public class TalkieClientSettings {
	public static final InetSocketAddress IntegratedServerAddress = new InetSocketAddress("localhost", 8080);
	public static final String IntegratedServerURL = "http://localhost:8080";
	
	public String openaiKey;
	public String serverUrl;
	
	/**
	 * Get an OpenAI Key and the Server URL by guessing and asking the user.
	 * @return The Settings configurated
	 */
	public static TalkieClientSettings SettingsConfigurator() {
		Scanner scanner = Main.UniversalScanner;
		System.out.println("Trying to Guess the Settings...");
		var info = new TalkieClientSettings();
		System.out.print("Talkie Server: Local... ");
		var isUp = detectServer(IntegratedServerAddress);
		if(isUp) {
			System.out.println("yes.");
			info.serverUrl = IntegratedServerURL;
		}else {
			System.out.println("no. Check that the server is running!");
		}
		System.out.println("OpenAI API Key: based on environment variables... ");
		
		var openaiKey = System.getenv("OPENAI_API_KEY");
		if(openaiKey != null) {
			System.out.println(openaiKey.substring(0, 4) + "...");
			info.openaiKey = openaiKey;
		}else {
			System.out.println("no. ");
		}
		
		System.out.println("Configuring unconfigured settings...");
		
		if(info.serverUrl == null) {
			System.out.print("Talkie Backend Server (with http://): ");
			info.serverUrl = scanner.nextLine();
		}
		if(info.openaiKey == null) {
			System.out.print("Your OpenAI Key: ");
			info.openaiKey = scanner.nextLine();
		}
		
		//scanner.close(); - Do not close a scanner anymore!
		System.out.println("Settings Configuration Done!");
		return info;
	}
	
	/**
	 * a Helper function that detects if a server is up.
	 * @param addr The server address
	 * @return true if the server is up, else false
	 */
	public static boolean detectServer(InetSocketAddress addr) {
		try {
			Socket s = new Socket();
			s.connect(addr, 10);
			s.close();
			return true;
		} catch (IOException e) {
			// TODO: handle exception
			// Ignore!
		}
		
		return false;
	}
}
