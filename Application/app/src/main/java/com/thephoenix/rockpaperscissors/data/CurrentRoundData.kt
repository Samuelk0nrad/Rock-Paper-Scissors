package com.thephoenix.rockpaperscissors.data

data class CurrentRoundData(
    var enemySelection: SelectionType,
    var playerSelection: SelectionType,
    var isEnemySelect: Boolean,
    var isPlayerSelect: Boolean
)
