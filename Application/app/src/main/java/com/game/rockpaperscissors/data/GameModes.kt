package com.game.rockpaperscissors.data

import android.util.Log
import com.game.rockpaperscissors.R
import com.game.rockpaperscissors.UiText

data class GameModes(
    val name: UiText,
    val description: UiText,
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
        name = UiText.StringResource(R.string.random),
        description = UiText.StringResource(R.string.random_description),
        number = 1,
        clickAction = {

        },
        symbol = R.drawable.whithwurfel,
        rout = "${Screen.GameSettingScreen.route}/RANDOM",
        available = true
    ),

    GameModes(
        name = UiText.StringResource(R.string.local_multiplayer),
        description = UiText.StringResource(R.string.local_multiplayer_description),
        number = 2,
        clickAction = {

        },
        symbol = R.drawable.white_multiplayer,
        rout = "${Screen.GameSettingScreen.route}/LOCAL_MULTIPLAYER",
        available = true
    ),
    GameModes(
        name = UiText.StringResource(R.string.online_multiplayer),
        description = UiText.StringResource(R.string.online_multiplayer_description),
        number = 3,
        clickAction = {

        },
        symbol = R.drawable.white_video_game,
        rout = "${Screen.GameSettingScreen.route}/ONLINE_MULTIPLAYER",
        available = true
    ),

    GameModes(
        name = UiText.StringResource(R.string.ai_mode),
        description = UiText.StringResource(R.string.ai_mode_description),
        number = 4,
        clickAction = {

        },
        symbol = R.drawable.white_microchip,
        rout = Screen.MainGame.route,
        available = false
    )
)
