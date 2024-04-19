package com.thephoenix.rockpaperscissors.data

import androidx.room.Entity


@Entity
class OneRound (
    val enemySelection: SelectionType,
    val playerSelection: SelectionType,
    val result: WinTyp,
)