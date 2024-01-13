package com.game.rockpaperscissors.data

data class PlayerPlayData(
    var selection: SelectionType,
    var isSelected: Boolean,
    var hide: Boolean,
    var isSelectable: Boolean,
    var isOnToSelect: Boolean
)
