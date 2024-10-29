package com.danifitdev.freetogameapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danifitdev.freetogameapp.data.local.dao.GamesDao
import com.danifitdev.freetogameapp.data.model.GameEntity


@Database(entities = [GameEntity::class], version = 2)
abstract class GamesAppDatabase : RoomDatabase() {
    abstract fun gamesDao(): GamesDao
}