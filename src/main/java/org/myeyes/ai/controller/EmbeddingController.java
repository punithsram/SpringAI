package org.myeyes.ai.controller;

import org.myeyes.ai.service.LlamaAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmbeddingController {

    @Autowired
    private LlamaAiService service;

    @PostMapping("/showSimilarityFinder")
    public ResponseEntity<float[]> embeddingExample(@RequestParam("conversationID") String conversationID, @RequestBody String text) {
        float[] result = service.getEmbed(text, conversationID);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/showSimilarityFinderMultiple")
    public ResponseEntity<Double> embeddingExample(@RequestParam String text1, @RequestParam String text2, @RequestParam("conversationID") String conversationID) {
        double result = service.getEmbedMultiples(text1, text2, conversationID);
        return ResponseEntity.ok(result);
    }
}
