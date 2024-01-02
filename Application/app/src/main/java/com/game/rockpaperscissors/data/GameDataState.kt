package com.game.rockpaperscissors.data

import com.game.rockpaperscissors.data.local.database.GameDataEntity

data class GameDataState (
    val allGames: List<GameDataEntity> = emptyList(),
    val mode: GameModesEnum = GameModesEnum.RANDOM,
    val rounds: Int = 0,
    val win: WinTyp? = null,
    val allRounds: List<OneRound> = emptyList(),
    val enemy: Enemy? = null,
)