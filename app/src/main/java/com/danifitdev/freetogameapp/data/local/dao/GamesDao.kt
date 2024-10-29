package com.danifitdev.freetogameapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.danifitdev.freetogameapp.data.model.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GameEntity>): List<Long>

    @Query("SELECT * FROM games_table")
    fun getAllGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM games_table WHERE id = :idGame")
    fun getGameById(idGame: Int?): Flow<GameEntity>

    @Update
    suspend fun updateGame(game: GameEntity)

    @Query("DELETE FROM games_table WHERE id = :gameId")
    suspend fun deleteGame(gameId: Int)
}