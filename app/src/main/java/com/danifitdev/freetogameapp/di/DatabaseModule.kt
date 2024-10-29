package com.danifitdev.freetogameapp.di

import android.content.Context
import androidx.room.Room
import com.danifitdev.freetogameapp.data.local.GamesAppDatabase
import com.danifitdev.freetogameapp.data.local.dao.GamesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideGamesDatabase(@ApplicationContext context: Context): GamesAppDatabase {
        return Room.databaseBuilder(
            context,
            GamesAppDatabase::class.java,
            "games_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideGamesDao(gamesAppDatabase: GamesAppDatabase): GamesDao {
        return gamesAppDatabase.gamesDao()
    }
}