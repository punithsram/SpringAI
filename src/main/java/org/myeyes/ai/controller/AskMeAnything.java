package org.myeyes.ai.controller;

import org.myeyes.ai.service.LlamaAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AskMeAnything {

    @Autowired
    private LlamaAiService llamaAiService;

    @PostMapping("/ask")
    public String askMeAnyThing(@RequestParam("question") String message) {
        String chat = llamaAiService.chat(message);
        System.out.println("Response From Ollama AI  " + chat);
        return chat;
    }

}
