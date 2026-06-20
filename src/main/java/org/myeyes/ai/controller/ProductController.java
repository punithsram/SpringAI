package org.myeyes.ai.controller;

import org.myeyes.ai.service.LlamaAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private LlamaAiService llamaAiService;
    @PostMapping("/productSearch")
    public String askMeAnyThing(@RequestParam("conversationID") String conversationID, @RequestBody String message) {
        String chat = llamaAiService.productSearch(message, conversationID);
        System.out.println("Response From Ollama AI  " + chat);
        return chat;
    }
}
