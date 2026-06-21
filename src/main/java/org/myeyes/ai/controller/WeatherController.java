package org.myeyes.ai.controller;

import org.myeyes.ai.service.LlamaAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @Autowired
    LlamaAiService llamaAiService;


    @PostMapping("/getWeather")
    public String weatherAgent(@RequestBody String query) {
        System.out.println("getWeather method called:---------- ");
        return llamaAiService.callAgent(query);
    }
}
