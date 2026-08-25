package com.ceestrong.steaminsights.service;

import com.ceestrong.steaminsights.dto.PlayerSummaryResponse;
import com.ceestrong.steaminsights.dto.PlayerSummaryResponse.Player;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SteamApiService {

    @Value("${steam.api.key}")
    private String apiKey;

    private final RestClient restClient = RestClient.create("https://api.steampowered.com");

    public Player getPlayerSummary(String steamId){
        PlayerSummaryResponse response = restClient.get()
                .uri("/ISteamUser/GetPlayerSummaries/v0002/?key={key}&steamids={steamId}", apiKey, steamId)
                .retrieve()
                .body(PlayerSummaryResponse.class);

        return response.response().players().get(0);
    }

}
