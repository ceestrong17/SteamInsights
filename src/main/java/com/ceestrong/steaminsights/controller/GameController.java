package com.ceestrong.steaminsights.controller;

import com.ceestrong.steaminsights.dto.AppDetailsResponse;
import com.ceestrong.steaminsights.service.SteamApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final SteamApiService steamApiService;

    public GameController(SteamApiService steamApiService) {
        this.steamApiService = steamApiService;
    }

    @GetMapping("/api/games/{appId}")
    public AppDetailsResponse.AppDetailsEntry getAppDetails(@PathVariable int appId){
        return steamApiService.getAppDetails(appId);
    }

}
