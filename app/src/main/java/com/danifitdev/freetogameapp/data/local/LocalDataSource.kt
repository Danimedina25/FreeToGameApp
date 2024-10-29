package com.danifitdev.freetogameapp.data.local

import com.danifitdev.freetogameapp.data.model.GameEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val gamesAppDatabase: GamesAppDatabase) {
     suspend fun insertGames(games: List<GameEntity>) {
        gamesAppDatabase.gamesDao().insertGames(games)
    }

    fun getAllGames(): Flow<List<GameEntity>> {
        return gamesAppDatabase.gamesDao().getAllGames()
    }

    fun getGameById(idGame: Int?): Flow<GameEntity> {
        return gamesAppDatabase.gamesDao().getGameById(idGame)
    }

    suspend fun updateGame(game: GameEntity) {
        gamesAppDatabase.gamesDao().updateGame(game)
    }

    suspend fun deleteGame(idGame: Int) {
        gamesAppDatabase.gamesDao().deleteGame(idGame)
    }
}