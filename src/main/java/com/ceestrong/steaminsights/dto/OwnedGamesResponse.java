package com.ceestrong.steaminsights.dto;

import java.util.List;

public record OwnedGamesResponse(SteamResponse response) {

    public record SteamResponse(List<Game> games, int game_count){}

    public record Game(
            int appid,
            String name,
            int playtime_forever,
            String img_icon_url
    ){}

}
