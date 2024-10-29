package com.danifitdev.freetogameapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games_table")
data class GameEntity(
    @PrimaryKey val id: Int?,
    val developer: String?,
    val freetogame_profile_url: String?,
    val game_url: String?,
    val genre: String?,
    val platform: String?,
    val publisher: String?,
    val release_date: String?,
    val short_description: String?,
    val thumbnail: String?,
    val title: String?
)