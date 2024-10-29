package com.danifitdev.freetogameapp.data.remote


import com.danifitdev.freetogameapp.data.model.GameEntity
import com.danifitdev.freetogameapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: GamesApiService) {
    suspend fun getAllGames(): NetworkResult<List<GameEntity>> {
        return try {
            val response = apiService.getGames()
            NetworkResult.Success(response)
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }
}