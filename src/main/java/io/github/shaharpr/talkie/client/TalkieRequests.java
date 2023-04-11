package io.github.shaharpr.talkie.client;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * A Retrofit Implementation of the Talkie API
 * @author shaha
 *
 */
public interface TalkieRequests {
	@GET("new")
	Call<String> newConversation(@Header("x-api-key") String key);
	
	@POST("talkie/{id}")
	Call<String> talkieGet(@Body String message, @Header("x-api-key") String apiKey,@Path("id") String conversationId);
	
	@DELETE("talkie/{id}")
	Call<Void> deleteConversation(@Header("x-api-key") String apiKey, @Path("id") String conversationId);
}
