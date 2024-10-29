package com.danifitdev.freetogameapp.data.remote

import com.danifitdev.freetogameapp.data.model.GameEntity
import retrofit2.http.GET

interface GamesApiService {
    @GET("/api/games")
    suspend fun getGames(): List<GameEntity>
}
