package org.myeyes.ai.controller;

import org.myeyes.ai.service.LlamaAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class StreamingController {




        @Autowired
        private LlamaAiService llamaAiService;

        @PostMapping("/stream")
        public Flux<String> streaming(@RequestParam("conversationID") String conversationID, @RequestBody String message) {
            return llamaAiService.streamAnswer(message, conversationID);
        }

}
