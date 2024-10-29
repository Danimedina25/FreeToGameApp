package com.danifitdev.freetogameapp.domain.usecases

import com.danifitdev.freetogameapp.domain.model.GameModel
import com.danifitdev.freetogameapp.domain.repository.GamesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllGamesUseCase @Inject constructor(private val repository: GamesRepository) {
    suspend operator fun invoke(): Flow<List<GameModel>> {
        return repository.getAllGames()
    }
}