package com.danifitdev.freetogameapp.domain.usecases

import com.danifitdev.freetogameapp.domain.model.GameModel
import com.danifitdev.freetogameapp.domain.repository.GamesRepository
import javax.inject.Inject

class UpdateGameUseCase @Inject constructor(private val repository: GamesRepository) {
    suspend operator fun invoke(game: GameModel) {
        repository.updateGame(game)
    }
}