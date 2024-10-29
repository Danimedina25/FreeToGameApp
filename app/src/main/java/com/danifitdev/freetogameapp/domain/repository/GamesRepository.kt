package com.danifitdev.freetogameapp.domain.repository

import com.danifitdev.freetogameapp.domain.model.GameModel
import com.danifitdev.freetogameapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow


interface GamesRepository {
    suspend fun fetchAndStoreGamesFromApi(): String
    suspend fun getAllGames(): Flow<List<GameModel>>
    suspend fun getGameById(idGame: Int?): Flow<GameModel>
    suspend fun updateGame(gameModel: GameModel)
    suspend fun deleteGame(idGame: Int)
}

