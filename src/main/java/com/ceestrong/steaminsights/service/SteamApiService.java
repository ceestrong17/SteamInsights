package com.ceestrong.steaminsights.service;

import com.ceestrong.steaminsights.dto.OwnedGamesResponse;
import com.ceestrong.steaminsights.dto.PlayerSummaryResponse;
import com.ceestrong.steaminsights.dto.PlayerSummaryResponse.Player;
import com.ceestrong.steaminsights.exception.InvalidSteamIdException;
import com.ceestrong.steaminsights.exception.NoGamesFoundException;
import com.ceestrong.steaminsights.exception.PlayerNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.List;

@Service
public class SteamApiService {

    @Value("${steam.api.key}")
    private String apiKey;

    private final RestClient restClient = RestClient.create("https://api.steampowered.com");

    public Player getPlayerSummary(String steamId){
        PlayerSummaryResponse response;
        try {
            response = restClient.get()
                    .uri("/ISteamUser/GetPlayerSummaries/v0002/?key={key}&steamids={steamId}", apiKey, steamId)
                    .retrieve()
                    .body(PlayerSummaryResponse.class);
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            throw new InvalidSteamIdException("Invalid Steam ID:" + steamId);
        }
        if(response.response().players().isEmpty()){
            throw new PlayerNotFoundException("Player not found for Steam ID: " + steamId);
        } else{
            return response.response().players().get(0);
        }
    }

    public List<OwnedGamesResponse.Game> getOwnedGames(String steamId){
        OwnedGamesResponse response;
        try {
            response = restClient.get()
                    .uri("/IPlayerService/GetOwnedGames/v1/?key={key}&steamid={steamId}&include_appinfo=true&include_played_free_games=true", apiKey, steamId)
                    .retrieve()
                    .body(OwnedGamesResponse.class);
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            throw new InvalidSteamIdException("Invalid Steam ID:" + steamId);
        }
        if(response.response().games() == null || response.response().games().isEmpty()){
            throw new NoGamesFoundException("No games found for Steam ID: " + steamId);
        } else {
            return response.response().games();
        }
    }

}
