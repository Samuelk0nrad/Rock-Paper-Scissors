package com.game.rockpaperscissors

class GameFunktions {
    private var firstSelection: Int = -1
    private var secondSelection: Int = -1
    private var result: String? = "0"

    public var wins:Int = 0
    public var loses:Int = 0

    constructor(ySelect: Int, eSelect: Int) {
        this.YourSelection = YourSelection
        this.EnemySelection = EnemySelection
    }

    constructor(YourSelection:Int) {
        this.YourSelection = YourSelection
    }

    constructor() {
    }

    public var YourSelection
        get() = firstSelection
        set(value) {
            if (value == 1 || value == 2 || value == 3) {
                firstSelection = value
            } else
                throw Exception("firstSelection/YourSelection can not be $value")
        }

    public var EnemySelection
        get() = secondSelection
        set(value) {
            if (value == 1 || value == 2 || value == 3) {
                secondSelection = value
            } else
                throw Exception("secondSelection/EnemySelection can not be $value")
        }

    public  var Result:String?
        get() {
            return result
        }
        set(value){}

    fun RandomEnemySelection() {
        var i = 0
        var count = 0
        do {
            i = 0
            try {
                EnemySelection = (1..3).random()
            } catch (e: Exception) {
                i = 1
            }
            count++
        } while (i == 1 && count <= 15)
    }


    fun Winer(ySelect: Int, eSelect: Int) {
        if (ySelect == 1) {
            result = when (eSelect) {
                1 -> "Draw"
                2 -> "Lose"
                3 -> "Win"
                else -> throw Exception("Winer faild by finding the Winder Your Selection is: ${firstSelection}, Enemy Selection is ${secondSelection}")
            }
        }

        if (ySelect == 2) {
            result = when (eSelect) {
                1 -> "Win"
                2 -> "Draw"
                3 -> "Lose"
                else -> throw Exception("Winer faild by finding the Winder Your Selection is: ${firstSelection}, Enemy Selection is ${secondSelection}")
            }
        }

        if (ySelect == 3) {
            result = when (eSelect) {
                1 -> "Lose"
                2 -> "Win"
                3 -> "Draw"
                else -> throw Exception("Winer faild by finding the Winder Your Selection is: ${firstSelection}, Enemy Selection is ${secondSelection}")
            }
        }

        when (result){
            "Win" -> wins++
            "Lose" -> loses++
        }
    }
}