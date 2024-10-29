package com.danifitdev.freetogameapp.domain.usecases

import com.danifitdev.freetogameapp.domain.repository.GamesRepository
import javax.inject.Inject

class FetchAndStoreGamesUseCase @Inject constructor(private val repository: GamesRepository) {
    suspend operator fun invoke(): String {
        return repository.fetchAndStoreGamesFromApi()
    }
}