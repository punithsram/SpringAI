package org.myeyes.ai.controller;

import org.myeyes.ai.service.LlamaAiService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobSearchController {

    @Autowired
    private LlamaAiService service;

    @PostMapping("/jobSearch")
    public ResponseEntity<List<Document>> jobSearch(@RequestParam("search") String search) {
        List<Document> documents = service.searchJob(search);
        return ResponseEntity.ok(documents);
    }

}
