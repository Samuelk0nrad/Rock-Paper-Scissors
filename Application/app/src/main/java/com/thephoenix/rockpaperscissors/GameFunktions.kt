package com.thephoenix.rockpaperscissors

import android.util.Log
import com.thephoenix.rockpaperscissors.data.SelectionType
import com.thephoenix.rockpaperscissors.data.SelectionTypeComparable
import com.thephoenix.rockpaperscissors.data.WinTyp

class GameFunktions {
    private var firstSelection: SelectionType? = null
    private var secondSelection: SelectionType? = null
    private var result: WinTyp = WinTyp.Draw

    constructor(ySelect: SelectionType, eSelect: SelectionType) {
        this.YourSelection = ySelect
        this.EnemySelection = eSelect
    }

    constructor(yourSelection:SelectionType) {
        this.YourSelection = yourSelection
    }

    constructor()

    var YourSelection: SelectionType?
        get() = firstSelection
        set(value) {
            firstSelection = value
        }

    var EnemySelection: SelectionType?
        get() = secondSelection
        set(value) {
            secondSelection = value
        }


    var Result:WinTyp?
        get() {
            return result
        }
        set(value){}

    fun randomEnemySelection():SelectionType {
        val res: SelectionType
        val range = 10000

        res = when((0..range).random()){
            in 0..(range/3) -> SelectionType.ROCK
            in (range/3)..(range/3*2) -> SelectionType.PAPER
            else -> SelectionType.SCISSORS

        }

        EnemySelection = res
        return res
    }


    fun winner(ySelect: SelectionType? = YourSelection, eSelect: SelectionType? = EnemySelection): WinTyp {
        if (ySelect == null || eSelect == null) {
            throw Exception("Your Selection or Enemy Selection is Null")
        }

        val yourSelection = SelectionTypeComparable(ySelect)
        val enemySelection = SelectionTypeComparable(eSelect)

        Log.d("GameSelection", "player: $ySelect, enemy: $eSelect ==> ${yourSelection.compareTo(enemySelection)}")


        return yourSelection.compareTo(enemySelection)
    }
}