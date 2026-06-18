package org.myeyes.ai.controller;

import org.myeyes.ai.service.LlamaAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobSearchController {

    @Autowired
    private LlamaAiService service;

    @PostMapping("/jobSearch")
    public String jobSearch(@RequestParam("conversationID") String conversationID) {
        return null;
    }

}
