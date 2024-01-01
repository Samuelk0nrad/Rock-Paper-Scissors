package com.game.rockpaperscissors.data

data class CurrentRoundData(
    var enemySelection: SelectionType,
    var playerSelection: SelectionType,
    var isEnemySelect: Boolean,
    var isPlayerSelect: Boolean
)
