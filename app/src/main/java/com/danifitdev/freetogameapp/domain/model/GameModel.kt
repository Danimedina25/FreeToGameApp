package com.danifitdev.freetogameapp.domain.model

data class GameModel(
    val id: Int? = 0,
    val developer: String? = "",
    val freetogame_profile_url: String? = "",
    val game_url: String? = "",
    val genre: String? = "",
    val platform: String? = "",
    val publisher: String? = "",
    val release_date: String? = "2023-08-08",
    val short_description: String? = "",
    val thumbnail: String? = "",
    val title: String? = ""
)