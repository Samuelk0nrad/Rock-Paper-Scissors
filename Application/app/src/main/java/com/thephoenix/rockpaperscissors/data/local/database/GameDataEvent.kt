package com.thephoenix.rockpaperscissors.data.local.database

import com.thephoenix.rockpaperscissors.data.Enemy
import com.thephoenix.rockpaperscissors.data.GameModesEnum
import com.thephoenix.rockpaperscissors.data.OneRound
import com.thephoenix.rockpaperscissors.data.WinTyp

sealed interface  GameDataEvent {
    object CreateNewPlayer: GameDataEvent
    data class SetMode(val mode: GameModesEnum): GameDataEvent
    data class SetRounds(val rounds: Int): GameDataEvent
    data class SetWin(val win: WinTyp): GameDataEvent
    data class SetNewRound(val oneRound: OneRound): GameDataEvent
    data class SetEnemy(val enemy: Enemy): GameDataEvent
    data class GetById(val id: Long): GameDataEvent
}