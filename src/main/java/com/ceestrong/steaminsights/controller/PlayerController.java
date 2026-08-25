package com.ceestrong.steaminsights.controller;

import com.ceestrong.steaminsights.dto.OwnedGamesResponse;
import com.ceestrong.steaminsights.dto.PlayerSummaryResponse;
import com.ceestrong.steaminsights.service.SteamApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerController {

    private final SteamApiService steamApiService;

    public PlayerController(SteamApiService steamApiService) {
        this.steamApiService = steamApiService;
    }

    @GetMapping("/api/players/{steamId}")
    public PlayerSummaryResponse.Player getPlayer(@PathVariable String steamId){
        return steamApiService.getPlayerSummary(steamId);
    }

    @GetMapping("/api/players/{steamId}/games")
    public List<OwnedGamesResponse.Game> getGames(@PathVariable String steamId){
        return steamApiService.getOwnedGames(steamId);
    }
}
