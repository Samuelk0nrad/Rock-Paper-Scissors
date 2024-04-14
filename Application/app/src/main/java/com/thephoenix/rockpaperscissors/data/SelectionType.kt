package com.thephoenix.rockpaperscissors.data

enum class SelectionType {
    ROCK,
    PAPER,
    SCISSORS;
}

class SelectionTypeComparable(
    private val selectionType: SelectionType
){
    fun compareTo(other: SelectionTypeComparable): WinTyp {
        return when {
            this.selectionType == other.selectionType -> WinTyp.Draw // It's a tie
            (this.selectionType == SelectionType.ROCK && other.selectionType == SelectionType.SCISSORS) ||
                    (this.selectionType == SelectionType.PAPER && other.selectionType == SelectionType.ROCK) ||
                    (this.selectionType == SelectionType.SCISSORS && other.selectionType == SelectionType.PAPER) -> WinTyp.Win // Current wins
            else -> WinTyp.Lose // Other wins
        }
    }
}
