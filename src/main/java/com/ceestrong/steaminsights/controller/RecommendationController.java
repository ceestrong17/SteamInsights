package com.ceestrong.steaminsights.controller;

import com.ceestrong.steaminsights.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService){
        this.recommendationService = recommendationService;
    }

    @GetMapping("api/recommendations/{steamId}/preferences")
    public Map<String, Double> getRecommendations(@PathVariable String steamId){
        return recommendationService.buildGenrePreferences(steamId);
    }
}
