package org.myeyes.ai.controller;

import org.myeyes.ai.service.LlamaAiService;
import org.springframework.ai.moderation.ModerationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModerationController {

    @Autowired
    private LlamaAiService llamaAiService;

    @PostMapping("/moderate")
    public ModerationResult moderate(@RequestBody String message){
      return llamaAiService.moderate(message);
    }
}
