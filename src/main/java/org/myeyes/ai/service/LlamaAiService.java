package org.myeyes.ai.service;

import org.myeyes.ai.agentstools.WeatherTools;
import org.myeyes.ai.dto.CountryCuisines;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.moderation.ModerationResult;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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

    public float[] getEmbed(String text) {
        return embeddingModel.embed(text);
    }

    public double getEmbedMultiples(String text, String text2) {
        List<float[]> response = embeddingModel.embed(List.of(text, text2));
        return cosineSimilarity(response.get(0), response.get(1));
    }

    /**
     * @param vectorA
     * @param vectorB Vectors A and B are close, while C is far away.
     *                Algorithms like cosine similarity or Euclidean distance measure this closeness.
     * @return
     */
    private double cosineSimilarity(float[] vectorA, float[] vectorB) {
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

    public String productSearch(String query, String conversationID) {
        return chatClient.prompt(query).
                advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID,
                        conversationID).advisors(QuestionAnswerAdvisor.builder(vectorStore).build())
                ).call().content();
    }

    public String callAgent(String query) {
        return chatClient.prompt(query).tools(new WeatherTools()).options(ChatOptions.builder()
                .temperature(1.0)
                .build()).call().content();
    }

    public ModerationResult moderate(String message) {
//        Ollama does not support a moderation we have to implement open api
//        Moderation is the process of checking whether user input or AI-generated output contains harmful, unsafe, or policy-violating content before or after the model responds.
//        Think of it as a content safety filter.

        return null;
    }

    public Flux<String> streamAnswer(String message, String conversationID) {
        return chatClient.prompt(message).options(ChatOptions.builder().temperature(1.0).maxTokens(600).build()).
                advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID,
                        conversationID)).
                stream().content();

    }
}
