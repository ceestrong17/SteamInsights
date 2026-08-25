package com.ceestrong.steaminsights.dto;

import java.util.List;

public record PlayerSummaryResponse(SteamResponse response) {

    public record SteamResponse(List<Player> players){}

    public record Player(
            String steamid,
            String personaname,
            String avatarfull,
            String profileurl
    ){}
}
