package com.danifitdev.freetogameapp.data.mappers

import com.danifitdev.freetogameapp.data.model.GameEntity
import com.danifitdev.freetogameapp.domain.model.GameModel
import javax.inject.Inject

class GamesMapper @Inject constructor() {
    fun mapGamesToDomain(games: List<GameEntity>): List<GameModel> {
        val listOfGames = games.map { game ->
            GameModel(
                developer = game.developer,
            freetogame_profile_url= game.freetogame_profile_url,
            game_url= game.game_url,
            genre= game.genre,
            id= game.id,
            platform= game.platform,
            publisher= game.publisher,
            release_date= game.release_date,
            short_description= game.short_description,
            thumbnail= game.thumbnail,
            title= game.title,
            )
        }
        return  listOfGames
    }

    fun mapGameToDomain(game: GameEntity): GameModel {
        return GameModel(
            developer = game.developer,
            freetogame_profile_url= game.freetogame_profile_url,
            game_url= game.game_url,
            genre= game.genre,
            id= game.id,
            platform= game.platform,
            publisher= game.publisher,
            release_date= game.release_date,
            short_description= game.short_description,
            thumbnail= game.thumbnail,
            title= game.title
        )
    }

    fun mapGameToEntity(game: GameModel): GameEntity {
        return GameEntity(
            developer = game.developer,
            freetogame_profile_url= game.freetogame_profile_url,
            game_url= game.game_url,
            genre= game.genre,
            id= game.id,
            platform= game.platform,
            publisher= game.publisher,
            release_date= game.release_date,
            short_description= game.short_description,
            thumbnail= game.thumbnail,
            title= game.title
        )
    }
}