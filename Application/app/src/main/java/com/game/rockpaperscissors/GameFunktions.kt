package com.game.rockpaperscissors

class GameFunktions {
    private var firstSelection: Int = -1
    private var secondSelection: Int = -1
    private var result: String = ""

    constructor(ySelect: Int, eSelect: Int) {
        this.YourSelection = YourSelection
        this.EnemySelection = EnemySelection
    }

    constructor(YourSelection:Int) {
        this.YourSelection = YourSelection
    }

    constructor()

    var YourSelection
        get() = firstSelection
        set(value) {
            if (value == 1 || value == 2 || value == 3) {
                firstSelection = value
            } else
                throw Exception("firstSelection/YourSelection can not be $value")
        }

    var EnemySelection: Int
        get() = secondSelection
        set(value) {
            if (value == 1 || value == 2 || value == 3) {
                secondSelection = value
            } else
                throw Exception("secondSelection/EnemySelection can not be $value")
        }

    var Result:String?
        get() {
            return result
        }
        set(value){}

    fun randomEnemySelection():Int {
        var res = 0
        val range = 10000

        res = when((0..range).random()){
            in 0..(range/3) -> 1
            in (range/3)..(range/3*2) -> 2
            else -> (1..3).random()

        }

        EnemySelection = res
        return res
    }


    fun Winer(ySelect: Int = YourSelection, eSelect: Int = EnemySelection): String {

        if (ySelect == 1) {
            result = when (eSelect) {
                1 -> "Draw"
                2 -> "Lose"
                3 -> "Win"
                else -> throw Exception("Winner failed by finding the Winder Your Selection is: ${firstSelection}, Enemy Selection is ${secondSelection}")
            }
        }

        if (ySelect == 2) {
            result = when (eSelect) {
                1 -> "Win"
                2 -> "Draw"
                3 -> "Lose"
                else -> throw Exception("Winner failed by finding the Winder Your Selection is: ${firstSelection}, Enemy Selection is ${secondSelection}")
            }
        }

        if (ySelect == 3) {
            result = when (eSelect) {
                1 -> "Lose"
                2 -> "Win"
                3 -> "Draw"
                else -> throw Exception("Winner failed by finding the Winder Your Selection is: ${firstSelection}, Enemy Selection is ${secondSelection}")
            }
        }


        return result
    }
}