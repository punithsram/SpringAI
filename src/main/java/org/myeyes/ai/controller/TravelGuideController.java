package org.myeyes.ai.controller;

import org.myeyes.ai.dto.CountryCuisines;
import org.myeyes.ai.service.LlamaAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TravelGuideController {

    @Autowired
    private LlamaAiService service;

    @PostMapping("/travelGuide")
    public String getTravelGuide(@RequestParam("city") String city, @RequestParam("month") String month, @RequestParam("language") String language,
                                 @RequestParam("budget") String budget, @RequestParam("conversationID") String conversationID) {
        return service.getTravelGuide(city, month, language, budget, conversationID);
    }


    @PostMapping("/cuisineHelper")
    public ResponseEntity<CountryCuisines> getCuisine(@RequestParam("country") String country, @RequestParam("numCuisines") String numCuisines, @RequestParam("language") String language
            , @RequestParam("conversationID") String conversationID) {

        CountryCuisines cuisine = service.getCuisine(country, numCuisines, language, conversationID);
        return ResponseEntity.ok(cuisine);
    }
}
