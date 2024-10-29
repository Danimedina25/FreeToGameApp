package com.danifitdev.freetogameapp.data.repository

import com.danifitdev.freetogameapp.data.local.LocalDataSource
import com.danifitdev.freetogameapp.data.mappers.GamesMapper
import com.danifitdev.freetogameapp.data.remote.RemoteDataSource
import com.danifitdev.freetogameapp.domain.model.GameModel
import com.danifitdev.freetogameapp.domain.repository.GamesRepository
import com.danifitdev.freetogameapp.utils.Constants
import com.danifitdev.freetogameapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.takeWhile
import javax.inject.Inject

class GamesRepositoryImpl  @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val gamesMapper: GamesMapper
): GamesRepository {

    override suspend fun fetchAndStoreGamesFromApi(): String {
        when(val games = remoteDataSource.getAllGames()){
            is NetworkResult.Success -> {
                localDataSource.insertGames(games.data)
                return Constants.MESSAGE_GET_API_DATA_SUCCESS
            }
            is NetworkResult.Error -> {
                return Constants.MESSAGE_GET_API_DATA_ERROR
            }
        }
    }

    override suspend fun getAllGames(): Flow<List<GameModel>> {
        return localDataSource.getAllGames().map { entities ->
            entities.map { gamesMapper.mapGameToDomain(it) }
        }
    }

    override suspend fun getGameById(idGame: Int?): Flow<GameModel> {
        return localDataSource.getGameById(idGame)
            .takeWhile { game -> game != null }
            .map { gamesMapper.mapGameToDomain(it) }
    }

    override suspend fun updateGame(gameModel: GameModel) {
        val gameEntity = gamesMapper.mapGameToEntity(gameModel)
        localDataSource.updateGame(gameEntity)
    }

    override suspend fun deleteGame(idGame: Int) {
        localDataSource.deleteGame(idGame)
    }
}