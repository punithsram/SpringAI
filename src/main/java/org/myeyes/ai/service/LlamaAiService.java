package org.myeyes.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class LlamaAiService {

    private final ChatClient chatClient;

    public LlamaAiService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    public String chat(String message) {
        return chatClient.prompt(message).call().content();
    }

}
