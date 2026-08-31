package com.ceestrong.steaminsights.dto;

import java.util.List;

public class AppDetailsResponse {

    public record AppDetailsEntry(boolean success, AppDetailsData data){}

    public record AppDetailsData(List<Genre> genres, List<Category> categories){}

    public record Genre(String id, String description){}

    public record Category(Integer id, String description){}
}
