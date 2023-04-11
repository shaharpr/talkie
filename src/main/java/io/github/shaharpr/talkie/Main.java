package io.github.shaharpr.talkie;

import java.util.Scanner;

import io.github.shaharpr.talkie.client.ServerErrorException;
import io.github.shaharpr.talkie.client.TalkieClient;
import io.github.shaharpr.talkie.client.TalkieClientSettings;
import io.github.shaharpr.talkie.client.TalkieRequests;

public class Main {
	public static Scanner UniversalScanner = new Scanner(System.in);
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		/*
		System.out.println("Talkie Test");
		var openaiKey = System.getenv("OPENAI_API_KEY");
		System.out.println("Loaded OpenAI API Key: " + openaiKey);
		TalkieGPTChat chat = new TalkieGPTChat(openaiKey);
		System.out.println("User: How much is 4*3?");
		var msg = chat.SendSimplifiedMessageAndCallBackend("How much is 4*3?");
		System.out.println("Assistant: " + msg);
		
		
		System.out.println("Getting a client from Retrofit...");
		TalkieRequests req = TalkieClient.getRequests("http://localhost:8080");
		
		System.out.println("Getting a Conversation ID from the server localhost:8080...");
		String conversationId = TalkieClient.newConversation(req, openaiKey);
		
		System.out.println("The Conversation ID is " + conversationId);
		System.out.println("User: Hello!");
		String out = TalkieClient.talkieGet(req, "Hello!", openaiKey, conversationId);
		System.out.println("Assistant: " + out);
		*/
		
		System.out.println("			Welcome to Talkie!			 ");
		System.out.println("=========================================");
		System.out.println("Talkie is a GPT-3.5-powered helpful chatbot.");
		System.out.println("With this app you can communicate with Talkie!");
		System.out.println("Please wait while we configure Talkie for you...");
		TalkieClientSettings settings = TalkieClientSettings.SettingsConfigurator();
		String input;
		String output;
		System.out.println("Talkie is Configured! Time to connect to the backend.");
		System.out.println("Please wait while we connect to Talkie...");
		TalkieRequests api = TalkieClient.getRequests(settings.serverUrl);
		String conversationId = TalkieClient.newConversation(api, settings.openaiKey);
		System.out.println("Your Conversation ID is: " + conversationId);
		System.out.println("Talkie is ready for you!");
		System.out.println("type EXIT to exit.");
		final Scanner scanner = UniversalScanner;
		while (true) {
			System.out.print("> ");
			input = scanner.nextLine();
			if(input.contains("EXIT")) break;
			try {
				output = TalkieClient.talkieGet(api, input, settings.openaiKey, conversationId);
				System.out.println(output);
			} catch (ServerErrorException e) {
				System.out.println("Oops! an error is occoured in the server-side!");
				System.out.println("Try again later...");
				System.out.println("Note for server operators: check the log of the server for exceptions");
				break;
			}
		}
		System.out.println("Deleting Chat...");
		TalkieClient.deleteConversation(api, settings.openaiKey, conversationId);
		System.out.println("Goodbye!");
		//scanner.close();
	}
}
