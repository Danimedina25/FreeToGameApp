package com.danifitdev.freetogameapp

import com.danifitdev.freetogameapp.domain.model.GameModel
import com.danifitdev.freetogameapp.domain.repository.GamesRepository
import com.danifitdev.freetogameapp.domain.usecases.GetAllGamesUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GetAllGamesUseCaseTest {

    @Mock
    private lateinit var repository: GamesRepository

    private lateinit var getAllGamesUseCase: GetAllGamesUseCase

    @Before
    fun setUp() {
        getAllGamesUseCase = GetAllGamesUseCase(repository)
    }

    @Test
    fun `invoke should call getAllGames on repository and return flow of games`() = runBlocking {
        // Arrange
        val expectedGames = listOf(
            GameModel(1, "Game 1", "https://example.com/game1", "Genre 1", "PC", "Publisher 1", "2023-08-08", "Short description 1", "https://example.com/thumbnail1.jpg"),
            GameModel(2, "Game 2", "https://example.com/game2", "Genre 2", "PC", "Publisher 2", "2023-08-08", "Short description 2", "https://example.com/thumbnail2.jpg")
        )

        // Simulamos el repositorio para que devuelva un Flow que emite expectedGames
        `when`(repository.getAllGames()).thenReturn(flowOf(expectedGames))

        // Act
        val actualFlow = getAllGamesUseCase()

        // Assert
        actualFlow.collect { result ->
            Assert.assertEquals(expectedGames, result) // Compara el resultado emitido
            // Verificamos que se llamó al método en el repositorio
            verify(repository).getAllGames()
        }
    }
}