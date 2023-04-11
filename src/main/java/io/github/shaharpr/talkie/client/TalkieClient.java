package io.github.shaharpr.talkie.client;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TalkieClient {
	public static TalkieRequests getRequests(String url) {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(url)
				.addConverterFactory(ScalarsConverterFactory.create())
				.build()
				;
		
		return retrofit.create(TalkieRequests.class);
	}
	
	public static String newConversation(TalkieRequests api, String key) throws Exception {
		var response = api.newConversation(key).execute();
		if(response.code() == 403) throw new KeyNotProvidedOrInvalidException("The API key is not provided");
		if(response.code() == 500) throw new ServerErrorException("Server Error.");
		if(response.isSuccessful()) return response.body();
		else throw new Exception("Unknown Response Code: " + response.code());
	}
	
	public static String talkieGet(TalkieRequests api, String message, String apiKey, String conversationId) throws Exception {
		var response = api.talkieGet(message, apiKey, conversationId).execute();
		if(response.code() == 403) throw new KeyNotProvidedOrInvalidException("The API key is invalid or not provided");
		if(response.code() == 404) throw new InvalidConversationIDException("The Conversation ID is Invalid!");
		if(response.code() == 500) throw new ServerErrorException("Server Error.");
		if(response.isSuccessful()) return response.body();
		else throw new Exception("Unknown Response Code: " + response.code());
	}
	
	public static void deleteConversation(TalkieRequests api, String key, String conversationId) throws Exception {
		var response = api.deleteConversation(key, conversationId).execute();
		if(response.code() == 403) throw new KeyNotProvidedOrInvalidException("The API key is invalid or not provided");
		if(response.code() == 500) throw new ServerErrorException("Server Error.");
		if(!response.isSuccessful()) throw new Exception("Unknown Response Code: " + response.code());
	}
}
