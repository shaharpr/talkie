package io.github.shaharpr.talkie.gptchat;

/**
 * Options to pass to the GPT-3.5
 * @author shaha
 *
 */
public class GPTOptions {
	/*
	 * We moved to a newer options system
	public static final String Model = "gpt-3.5-turbo";
	public static final int MaxTokens = 256;
	public static final double Temperature = 0.7;
	public static final String MESSAGE_ASSISTANT = "assistant";
	public static final String MESSAGE_USER = "user";
	*/
	
	/**
	 * Options to pass to the GPT-3.5
	 * @author shaha
	 *
	 */
	public static class Options {
		/**
		 * The model to use
		 */
		public String Model;
		/**
		 * Maximum (total) tokens.
		 */
		public int MaxTokens;
		/**
		 * The temperature of the model
		 */
		public double Temperature;
		/**
		 * The assistant message type name to use
		 */
		public String AssistantMessage;
		/**
		 * The user message type name to use
		 */
		public String UserMessage;
		
		/**
		 * Create an options object
		 * @param model
		 * @param maxTokens
		 * @param temperature
		 * @param assistantMsg
		 * @param usrMsg
		 */
		public Options(String model, int maxTokens, double temperature, String assistantMsg, String usrMsg) {
			this.Model = model;
			this.MaxTokens = maxTokens;
			this.Temperature = temperature;
			this.AssistantMessage = assistantMsg;
			this.UserMessage = usrMsg;
		}
	}
	
	/**
	 * The default settings used by Talkie
	 * @author shaha
	 *
	 */
	public static class DefaultTalkieOptions extends Options {
		/**
		 * Create an options object with a model selection
		 * @param model The model to use
		 */
		public DefaultTalkieOptions(String model) {
			super(model == null ? "gpt-3.5-turbo" : model, 256, 0.7, "assistant", "user");
		}
		
		/**
		 * Create an options object with the default model
		 */
		public DefaultTalkieOptions() {
			this(null);
		}
	}
	
	public static class GPTModels {
		/**
		 * The well-known GPT-3.5 Model. Known as ChatGPT. Very recommended.
		 */
		public static final String GPT3_5_Turbo = "gpt-3.5-turbo";
		/**
		 * The GPT-3.5 Model from March 1st - The release date of GPT-3.5
		 */
		public static final String GPT3_5_Turbo_1_March = "gpt-3.5-turbo-0301";
		/**
		 * InstructGPT - The latest GPT-3 Model - Davinci version. Not recommended.
		 */
		public static final String Davinci003 = "text-davinci-003";
		/**
		 * Davinci - the base model of GPT-3. it's deprecated and very not recommended
		 * @deprecated This is the base model! it's mentioned only for archiving
		 */
		@Deprecated()
		public static final String Davinci = "davinci";
	}
}
