package com.game.rockpaperscissors.data.local.database

import com.game.rockpaperscissors.data.Enemy
import com.game.rockpaperscissors.data.GameModesEnum
import com.game.rockpaperscissors.data.OneRound
import com.game.rockpaperscissors.data.WinTyp

sealed interface  GameDataEvent {
    object CreateNewPlayer: GameDataEvent
    data class SetMode(val mode: GameModesEnum): GameDataEvent
    data class SetRounds(val rounds: Int): GameDataEvent
    data class SetWin(val win: WinTyp): GameDataEvent
    data class SetNewRound(val oneRound: OneRound): GameDataEvent
    data class SetEnemy(val enemy: Enemy): GameDataEvent
}