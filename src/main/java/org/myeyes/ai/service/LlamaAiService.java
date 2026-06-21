package org.myeyes.ai.service;

import org.myeyes.ai.controller.CountryCuisines;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.moderation.ModerationResult;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LlamaAiService {

    private final ChatClient chatClient;

    @Autowired
    private EmbeddingModel embeddingModel;

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
}
