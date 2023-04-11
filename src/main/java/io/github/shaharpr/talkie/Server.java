package io.github.shaharpr.talkie;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.hash.Hashing;

import io.github.shaharpr.talkie.talkiechat.TalkieGPTChat;
import io.javalin.Javalin;
import io.javalin.http.ContentType;

/**
 * a RESTful Talkie!
 * @author shaha
 *
 */
public class Server {
	public static final boolean UseJsonize = false;
	public static HashMap<String, TalkieGPTChat> TalkieChats = new HashMap<>();
	public static Logger logger = LoggerFactory.getLogger(Server.class);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		var app = Javalin.create();
		app.get("/", ctx -> {
			ctx.contentType(ContentType.HTML);
			ctx.result("You are connected to the Talkie server interface");
			
		});
		
		app.get("/new", ctx -> {
			var key = ctx.header("x-api-key");
			if(key == null) {
				ctx.status(403);
				ctx.result("a Key is required!");
				return;
			}
			ctx.status(200);
			ctx.result(Jsonize(newConversation(key)));
		});
		
		app.post("/talkie/{id}", ctx -> {
			var key = ctx.header("x-api-key");
			if(key == null) {
				ctx.status(403);
				ctx.result("a Key is required!");
				return;
			}
			
			var conversationId = ctx.pathParam("id");
			var msg = ctx.body();
			if(!keyIsCorrect(conversationId, key)) {
				ctx.status(403);
				ctx.result("API key or Conversation ID is invalid!");
				return;
			}
			
			logger.info("New Message: " + msg + "; At Conversation ID: " + conversationId +"; Sending...");
			var output = sendMessage(conversationId, msg);
			if(output == null) {
				ctx.status(404);
				ctx.result("The conversation ID " + conversationId + " was not found.");
				logger.warn("The Conversation ID " + conversationId + " was not found");
				return;
			}
			
			logger.info("Sent! Output: " + output);
			ctx.status(200);
			ctx.result(Jsonize(output));
		});
		
		app.delete("/talkie/{id}", ctx -> {
			var key = ctx.header("x-api-key");
			var conversationId = ctx.pathParam("id");
			if(key == null) {
				ctx.status(403);
				ctx.result("An API key is required for the authentication.");
				return;
			}
			if(!keyIsCorrect(conversationId, key)) {
				ctx.status(403);
				ctx.result("The key provided is not the same as the key of the conversation ID creator");
				return;
			}
			
			deleteConversation(conversationId);
			ctx.status(204);
		});
		
		logger.info("Starting server...");
		
		app.start(8080);
	}
	
	public static String newConversation(String key) {
		Random rand = new Random();
		String conversationId = Hashing.sha256().hashString(key, Charset.defaultCharset()).toString();
		Integer randNum = rand.nextInt(0, 255);
		conversationId = conversationId + "-" + randNum.toString();
		
		logger.info("New Conversation - " + conversationId + " - Starting chat...");
		
		TalkieChats.put(conversationId, new TalkieGPTChat(key));
		return conversationId;
	}
	
	public static void deleteConversation(String conversationId) {
		logger.info("Deleting Conversation at ID: " + conversationId);
		TalkieChats.remove(conversationId);
		logger.info("Deleted!");
	}
	
	public static boolean keyIsCorrect(String conversationId, String key) {
		var idHash = conversationId.split("-")[0];
		var keyHash = Hashing.sha256().hashString(key, Charset.defaultCharset()).toString();
		return idHash.equals(keyHash);
	}
	
	public static String sendMessage(String conversationId, String message) throws Exception {
		var chat = TalkieChats.get(conversationId);
		if(chat == null) return null;
		return chat.SendSimplifiedMessageAndCallBackend(message);
	}
	
	public static String Jsonize(String str) {
		if(UseJsonize) return "\"" + str + "\"";
		else return str;
	}
}
