package com.ceestrong.steaminsights.service;

import com.ceestrong.steaminsights.dto.AppDetailsResponse;
import com.ceestrong.steaminsights.dto.OwnedGamesResponse;
import com.ceestrong.steaminsights.exception.AppDetailsNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecommendationService {

    private final SteamApiService steamApiService;
    public RecommendationService(SteamApiService steamApiService) {
        this.steamApiService = steamApiService;
    }

    public List<Integer> getCandidateAppIds(){
        return List.of(730, 570, 578080, 271590, 1091500, 1245620, 413150, 292030, 1174180, 620, 400,
                220, 440, 105600, 892970, 359550, 4000, 252490, 550, 304930, 227300, 236390, 218620, 431240,
                1145360, 1794680, 646570, 289070, 8930, 374320, 1085660, 230410, 582010, 294100, 275850,
                365590, 1966720, 391540, 244850, 393380, 232090, 205790, 239140, 203160, 391220, 379720,
                1119010, 1240440, 812140, 1517290);
    }

    public Map<String, Double> buildGenrePreferences(String steamId){
        Map<String, Double> genrePreferences = new HashMap<>();
        List<OwnedGamesResponse.Game> games = steamApiService.getOwnedGames(steamId);
        for(OwnedGamesResponse.Game game : games){
            List<AppDetailsResponse.Genre> genres;
            try {
                genres = steamApiService.getAppDetails(game.appid()).data().genres();
                if(genres == null){
                    continue;
                }
            } catch (AppDetailsNotFoundException e) {
                continue;
            }
            for(AppDetailsResponse.Genre genre : genres){
                double currentTime = genrePreferences.getOrDefault(genre.description(), 0.0);
                genrePreferences.put(genre.description(), currentTime + (double) game.playtime_forever());
            }
        }
        return genrePreferences;
    }

}
