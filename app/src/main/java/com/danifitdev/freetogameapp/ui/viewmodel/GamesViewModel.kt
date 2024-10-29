package com.danifitdev.freetogameapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danifitdev.freetogameapp.domain.model.GameModel
import com.danifitdev.freetogameapp.domain.usecases.DeleteGameUseCase
import com.danifitdev.freetogameapp.domain.usecases.FetchAndStoreGamesUseCase
import com.danifitdev.freetogameapp.domain.usecases.GetAllGamesUseCase
import com.danifitdev.freetogameapp.domain.usecases.GetGameByIdUseCase
import com.danifitdev.freetogameapp.domain.usecases.UpdateGameUseCase
import com.danifitdev.freetogameapp.utils.Constants
import com.danifitdev.freetogameapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val fetchAndStoreGamesUseCase: FetchAndStoreGamesUseCase,
    private val getAllGamesUseCase: GetAllGamesUseCase,
    private val getGameByIdUseCase: GetGameByIdUseCase,
    private val updateGameUseCase: UpdateGameUseCase,
    private val deleteGameUseCase: DeleteGameUseCase

): ViewModel() {

    private val _games = MutableStateFlow<List<GameModel>>(emptyList())
    val games: StateFlow<List<GameModel>> = _games.asStateFlow()

    private val _game = MutableStateFlow<GameModel?>(GameModel())
    val game: StateFlow<GameModel?> = _game.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _successMessage = MutableStateFlow<String?>(null) // Estado inicial null
    val successMessage: StateFlow<String?> = _successMessage

    private val _errorMessage = MutableStateFlow<String?>(null) // Estado inicial null
    val errorMessage: StateFlow<String?> = _errorMessage


    fun fetchGames() {
        viewModelScope.launch {
            val result = fetchAndStoreGamesUseCase()
            if(result == Constants.MESSAGE_GET_API_DATA_SUCCESS){
                _successMessage.value = result
            }else{
                _errorMessage.value = result
            }
        }
    }

    fun getGamesFromDb() {
        viewModelScope.launch {
            getAllGamesUseCase().collect { gamesList ->
                _games.value = gamesList
            }
        }
    }

    fun getGamesById(idGame: Int) {
        viewModelScope.launch {
            getGameByIdUseCase(idGame).collect { game ->
                _game.value = game
            }
        }
    }

    fun updateGame(updatedGame: GameModel) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                updateGameUseCase(updatedGame)
                _successMessage.value = "Datos actualizados exitosamente"
            } catch (e: Exception) {
                _errorMessage.value = "Error al actualizar los datos del juego"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteGame(idGame: Int){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                deleteGameUseCase(idGame)
                _successMessage.value = "Registro eliminado exitosamente"
            } catch (e: Exception) {
                _errorMessage.value = "Error al intentar eliminar el juego"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearMessages() {
        _successMessage.value = null
        _errorMessage.value = null
    }
}