package com.game.rockpaperscissors.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class GameData{
    var rounds: Int by mutableIntStateOf(0)
    var currentRound: Int by mutableIntStateOf(1)
    var playerWins: Int by mutableIntStateOf(0)
    var enemyWins: Int by mutableIntStateOf(0)

    constructor(rounds: Int, currentRound: Int, playerWins: Int, enemyWins: Int){
        this.rounds = rounds
        this.currentRound = currentRound
        this.playerWins = playerWins
        this.enemyWins = enemyWins
    }
}
