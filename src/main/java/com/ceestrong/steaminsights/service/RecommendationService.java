package com.ceestrong.steaminsights.service;

import com.ceestrong.steaminsights.dto.AppDetailsResponse;
import com.ceestrong.steaminsights.dto.OwnedGamesResponse;
import com.ceestrong.steaminsights.dto.RecommendedGame;
import com.ceestrong.steaminsights.exception.AppDetailsNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public Map<String, Double> buildGenrePreferences(List<OwnedGamesResponse.Game> ownedGamesResponse){
        Map<String, Double> genrePreferences = new HashMap<>();
        for(OwnedGamesResponse.Game game : ownedGamesResponse){
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

    public double scoreGame(int appId, Map<String, Double> genrePreferences){
        List<AppDetailsResponse.Genre> genres;
        double score = 0.0;
        try {
            genres = steamApiService.getAppDetails(appId).data().genres();
            if(genres == null){
                return 0.0;
            }
        } catch (AppDetailsNotFoundException e) {
            return 0.0;
        }
        for(AppDetailsResponse.Genre genre : genres){
            score += genrePreferences.getOrDefault(genre.description(), 0.0);
        }
        return score;
    }

    public List<RecommendedGame> getRecommendations(String steamId, int topN){
        List<OwnedGamesResponse.Game> games = steamApiService.getOwnedGames(steamId);
        Map<String, Double> genrePreferences = buildGenrePreferences(games);

        Set<Integer> ownedGames = games.stream().map(OwnedGamesResponse.Game::appid).collect(Collectors.toSet());

        List<Integer> candidateAppIds = getCandidateAppIds();

        Map<Integer, Double> candidateScores = new HashMap<>();

        for(int appId : candidateAppIds){
            if(ownedGames.contains(appId)){
                continue;
            }
            candidateScores.put(appId, scoreGame(appId, genrePreferences));
        }

        return toRecommendedGames(candidateScores.entrySet().stream().sorted(Map.Entry.<Integer, Double>comparingByValue().reversed()).limit(topN).map(Map.Entry::getKey).collect(Collectors.toList()));

    }

    private List<RecommendedGame> toRecommendedGames(List<Integer> appIds){
        List<RecommendedGame> recommendedGames = new ArrayList<>();

        for(int appId : appIds){
            try {
                recommendedGames.add(new RecommendedGame(appId, steamApiService.getAppDetails(appId).data().name()));
            }catch (AppDetailsNotFoundException e){
                continue;
            }
        }

        return recommendedGames;
    }

    public Map<String, Double> buildGenrePreferences(String steamId){
        List<OwnedGamesResponse.Game> games = steamApiService.getOwnedGames(steamId);
        return buildGenrePreferences(games);
    }

}
