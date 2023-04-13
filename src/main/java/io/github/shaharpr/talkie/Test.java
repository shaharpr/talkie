package io.github.shaharpr.talkie;

import io.github.shaharpr.talkie.gptchat.TemperatureChooser;
import io.github.shaharpr.talkie.utils.DateTime;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("The date-Time is: " + DateTime.getTimeNow());
		String openaiKey = System.getenv("OPENAI_API_KEY");
		TemperatureChooser.initTempChooser(openaiKey);
		System.out.print("The category of 'Hi! how are you?' is: ");
		System.out.println(TemperatureChooser.getCategory("Hi! how are you?", openaiKey));
		System.out.print("The suggested temperature to the request with the message 'What is the hour now?' is: ");
		
		System.out.println(TemperatureChooser.getReccommendedTemp(openaiKey, "What is the hour now?"));
	}

}
