package org.myeyes.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.stereotype.Service;

@Service
public class LlamaAiService {

    private final ChatClient chatClient;

    public LlamaAiService(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        this.chatClient = chatClientBuilder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
    }

    public String chat(String message, String conversationID) {
//        replace with real time userID
        return chatClient.prompt(message).options(OllamaChatOptions.builder().temperature(1.0).maxTokens(600)).
                advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID,
                        conversationID)).
                call().content();
    }

    /*
     Note: Chat memory and Advisor
     it automatically includes conversation history (chat memory) with every request.
     An Advisor in Spring AI is an interceptor that runs before and/or after the LLM call. Advisors can:
     Add chat history
     Add retrieved documents (RAG)
     Log requests/responses
     Modify prompts
     Track metrics
     Apply custom logic

     chatMemory
     This is the storage for the conversation history.
     It could be:
     InMemoryChatMemory
     JdbcChatMemory
     CassandraChatMemory
     RedisChatMemory
     Any custom implementation

     The common types of advisors are:
     Advisor	Purpose
     MessageChatMemoryAdvisor:	Adds previous chat messages to the prompt and updates chat memory.
     PromptChatMemoryAdvisor:	Injects chat history into the prompt as formatted text instead of individual message objects. Useful for models that don't support message-based conversations well.
     QuestionAnswerAdvisor:	Performs Retrieval-Augmented Generation (RAG) by retrieving relevant documents from a VectorStore and adding them to the prompt.
     SimpleLoggerAdvisor:	Logs the request and response for debugging and development.
     We can use multiple advisors together.
     Custom Advisor	Add your own pre/post processing	Security, personalization, analytics
     */


    /*
    Chat Options
    ChatOptions in Spring AI is used to configure how the LLM generates responses. Instead of changing the prompt, it changes the model's behavior (temperature, max tokens, model name, etc.).

    Think of it like this:

    Prompt → What you ask the model.
    ChatOptions → How the model should answer.
    Creating ChatOptions

    Most providers have their own options class, such as:

    OpenAiChatOptions
    AzureOpenAiChatOptions
    VertexAiGeminiChatOptions
    OllamaOptions

        Option	Purpose
            model	--> Select the model to use
            temperature 	--> 	Control creativity vs. determinism
            maxTokens	--> 	Limit response length
            topP	--> 	Control token sampling
            frequencyPenalty	--> 	Reduce repetition
            presencePenalty		--> Encourage new ideas
            stop		--> Define stopping sequences

            In practice, the three options you'll use most often are:

            model – choose the LLM.
            temperature – control creativity.
            maxTokens – control response length.
     */
}
