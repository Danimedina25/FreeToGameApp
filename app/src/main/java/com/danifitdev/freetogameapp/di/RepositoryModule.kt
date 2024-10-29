package com.danifitdev.freetogameapp.di

import com.danifitdev.freetogameapp.data.repository.GamesRepositoryImpl
import com.danifitdev.freetogameapp.domain.repository.GamesRepository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindGamesRepository(
        repositoryImpl: GamesRepositoryImpl
    ): GamesRepository
}