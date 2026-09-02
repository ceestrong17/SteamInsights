package com.ceestrong.steaminsights.controller;

import com.ceestrong.steaminsights.dto.RecommendedGame;
import com.ceestrong.steaminsights.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService){
        this.recommendationService = recommendationService;
    }


    @GetMapping("/api/recommendations/{steamId}/preferences")
    public Map<String, Double> getPreferences(@PathVariable String steamId){
        return recommendationService.buildGenrePreferences(steamId);
    }

    @GetMapping("/api/recommendations/{steamId}")
    public List<RecommendedGame> getRecommendations(@PathVariable String steamId, @RequestParam(defaultValue = "10") int topN){
        return recommendationService.getRecommendations(steamId, topN);
    }
}
