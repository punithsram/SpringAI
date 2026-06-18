package org.myeyes.ai.service;

import org.myeyes.ai.controller.CountryCuisines;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LlamaAiService {

    private final ChatClient chatClient;

    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private VectorStore vectorStore;

    public LlamaAiService(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        this.chatClient = chatClientBuilder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
    }

    public String chat(String message, String conversationID) {
//        replace with real time userID
        return chatClient.prompt(message).options(ChatOptions.builder().temperature(1.0).maxTokens(600).build()).
                advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID,
                        conversationID)).
                call().content();
    }

    public String getTravelGuide(String city, String month, String language, String budget, String conversationID) {
        PromptTemplate travelTemplate = new PromptTemplate("Welcome to the {city} travel guide!\n"
                + "if you are visiting in {month}. here's what you can do:\n"
                + "1. Must-visit Attraction.\n"
                + "2. Local cruise you must try.\n"
                + "3. Useful phrases in {language}. \n"
                + "4. Tips for Travelling on {budget} budget.\n"
                + "Enjoy your trip!!!!!!!!!!\n");
        Prompt prompt = travelTemplate.create(Map.of("city", city, "month", month, "language", language, "budget", budget));
        return chatClient.prompt(prompt).
                advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID,
                        conversationID)).
                call().content();
    }

    public CountryCuisines getCuisine(String country, String numCuisines, String language, String conversationID) {
        PromptTemplate travelTemplate = new PromptTemplate("You are an expert in traditional cuisines.!\n"
                + "Answer the Question: What is the traditional cuisine of {country}\n"
                + "Return a list of {numCuisines} in {language}.\n"
        );
        Prompt prompt = travelTemplate.create(Map.of("country", country, "numCuisines", numCuisines, "language", language));
        return chatClient.prompt(prompt).
                advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID,
                        conversationID)).
                call().entity(CountryCuisines.class);
    }

    public float[] getEmbed(String text, String conversationID) {
        return embeddingModel.embed(text);
    }

    public double getEmbedMultiples(String text, String text2, String conversationID) {
        List<float[]> response = embeddingModel.embed(List.of(text, text2));
        return cosineSimmilarity(response.get(0), response.get(1));
    }

    /**
     * @param vectorA
     * @param vectorB
     * Vectors A and B are close, while C is far away.
     * Algorithms like cosine similarity or Euclidean distance measure this closeness.
     * @return
     */
    private double cosineSimmilarity(float[] vectorA, float[] vectorB) {
        if (vectorA.length != vectorB.length) {
            throw new IllegalArgumentException("Vectors must be same length");
        }

//        initializing variables for dot and magnitudes
        double dotProduct = 0.0;
        double magnitudeA = 0.0;
        double magnitudeB = 0.0;
//          calculate dot product and magnitudes
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct = dotProduct + vectorA[i] * vectorB[i];
            magnitudeA = magnitudeA + vectorA[i] * vectorA[i];
            magnitudeB = magnitudeB + vectorB[i] * vectorB[i];
        }

//        calculate and return cosine similarity
        return dotProduct / (Math.sqrt(magnitudeA) * Math.sqrt(magnitudeB));

    }

    public List<Document> searchJob(String query) {
        return vectorStore.similaritySearch(SearchRequest.builder().topK(3).query(query).build());
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


    /*
    Embedding
     */
}
