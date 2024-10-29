package com.danifitdev.freetogameapp

import com.danifitdev.freetogameapp.domain.repository.GamesRepository
import com.danifitdev.freetogameapp.domain.usecases.FetchAndStoreGamesUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchAndStoreGamesUseCaseTest {

    @Mock
    private lateinit var repository: GamesRepository

    private lateinit var fetchAndStoreGamesUseCase: FetchAndStoreGamesUseCase

    @Before
    fun setUp() {
        fetchAndStoreGamesUseCase = FetchAndStoreGamesUseCase(repository)
    }

    @Test
    fun `invoke should call fetchAndStoreGamesFromApi on repository and return success message`() = runBlocking {
        // Arrange
        val expectedMessage = "Games fetched and stored successfully"
        `when`(repository.fetchAndStoreGamesFromApi()).thenReturn(expectedMessage)

        // Act
        val actualMessage = fetchAndStoreGamesUseCase()

        // Assert
        verify(repository).fetchAndStoreGamesFromApi()
        assertEquals(expectedMessage, actualMessage)
    }
}