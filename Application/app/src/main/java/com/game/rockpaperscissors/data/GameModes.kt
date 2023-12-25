package com.game.rockpaperscissors.data

import android.util.Log
import com.game.rockpaperscissors.R

data class GameModes(
    val name: String,
    val description: String,
    var number: Int,
    val clickAction: () -> Unit,
    val symbol: Int,
    val rout: String,
    val available: Boolean
){
    fun setToFirst(gameModes: List<GameModes>){
        val currentNumber = number

        for(gameMode in gameModes){
            if(gameMode.number < currentNumber){
                gameMode.number++
            }
        }

        number = 1

        for(gameMode in gameModes){
            Log.d("GameMode", "${gameMode.name}: ${gameMode.number}")
        }

        Log.d("GameMode", "")
    }
}

var gameModes: List<GameModes> = mutableListOf(
    GameModes(
        name = "Random",
        description = "Play agens a random selector",
        number = 1,
        clickAction = {

        },
        symbol = R.drawable.welcome_ilustration,
        rout = Screen.MainGame.route,
        available = true
    ),

    GameModes(
        name = "Local Multiplayer",
        description = "Play With a ",
        number = 2,
        clickAction = {

        },
        symbol = R.drawable.welcome_ilustration,
        rout = Screen.MainGame.route,
        available = false
    ),
    GameModes(
        name = "Online Multiplayer",
        description = "Play with other player",
        number = 3,
        clickAction = {

        },
        symbol = R.drawable.welcome_ilustration,
        rout = Screen.MainGame.route,
        available = false
    ),

    GameModes(
        name = "AI Mode",
        description = "Play With a Computer AI",
        number = 4,
        clickAction = {

        },
        symbol = R.drawable.welcome_ilustration,
        rout = Screen.MainGame.route,
        available = false
    )
)
