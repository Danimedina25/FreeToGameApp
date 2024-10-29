package com.danifitdev.freetogameapp

import com.danifitdev.freetogameapp.data.local.LocalDataSource
import com.danifitdev.freetogameapp.data.local.dao.GamesDao
import com.danifitdev.freetogameapp.data.mappers.GamesMapper
import com.danifitdev.freetogameapp.data.model.GameEntity
import com.danifitdev.freetogameapp.data.remote.GamesApiService
import com.danifitdev.freetogameapp.data.remote.RemoteDataSource
import com.danifitdev.freetogameapp.data.repository.GamesRepositoryImpl
import com.danifitdev.freetogameapp.domain.model.GameModel
import com.danifitdev.freetogameapp.utils.NetworkResult
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception

// Ejecuta la prueba con MockitoJUnitRunner
@RunWith(MockitoJUnitRunner::class)
class GameRepositoryImplTest {

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var localDataSource: LocalDataSource

    @Mock
    private lateinit var gamesMapper: GamesMapper

    private lateinit var gamesRepository: GamesRepositoryImpl

    @Before
    fun setUp() {
        gamesRepository = GamesRepositoryImpl(remoteDataSource, localDataSource, gamesMapper)
    }

    @Test
    fun `fetchAndStoreGamesFromApi should insert games when API returns success`() = runBlocking {
        val mockGamesData = listOf(GameEntity(0, "prueba", "prueba",
            "prueba","prueba","prueba","prueba","prueba",
            "prueba", "prueba","prueba"))
        val networkResult = NetworkResult.Success(mockGamesData)

        `when`(remoteDataSource.getAllGames()).thenReturn(networkResult)

        gamesRepository.fetchAndStoreGamesFromApi()

        verify(localDataSource).insertGames(mockGamesData)
    }

    @Test
    fun `fetchAndStoreGamesFromApi should not insert games when API returns error`() = runBlocking {
        val networkError = NetworkResult.Error(Throwable())
        `when`(remoteDataSource.getAllGames()).thenReturn(networkError)

        gamesRepository.fetchAndStoreGamesFromApi()

        verify(localDataSource, never()).insertGames(emptyList())
    }

    @Test
    fun `getGameById should return mapped game from local data source`() = runBlocking {
        val mockEntity = GameEntity(0, "prueba", "prueba",
            "prueba","prueba","prueba","prueba","prueba",
            "prueba", "prueba","prueba")
        val mockDomainModel = GameModel()

        `when`(localDataSource.getGameById(any())).thenReturn(flowOf(mockEntity))
        `when`(gamesMapper.mapGameToDomain(mockEntity)).thenReturn(mockDomainModel)

        val result = gamesRepository.getGameById(1).toList().first()

        assertEquals(mockDomainModel, result)
    }

    @Test
    fun `updateGame should map and update game in local data source`() = runBlocking {
        val mockDomainModel = GameModel()
        val mockEntity = GameEntity(0, "prueba", "prueba",
            "prueba","prueba","prueba","prueba","prueba",
            "prueba", "prueba","prueba")

        `when`(gamesMapper.mapGameToEntity(mockDomainModel)).thenReturn(mockEntity)

        gamesRepository.updateGame(mockDomainModel)

        verify(localDataSource).updateGame(mockEntity)
    }

    @Test
    fun `deleteGame should delete game by id in local data source`() = runBlocking {
        val gameId = 1
        gamesRepository.deleteGame(gameId)
        verify(localDataSource).deleteGame(gameId)
    }
}