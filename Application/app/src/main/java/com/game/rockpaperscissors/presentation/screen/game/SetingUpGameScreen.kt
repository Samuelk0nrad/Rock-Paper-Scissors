package com.game.rockpaperscissors.presentation.screen.game

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.game.rockpaperscissors.presentation.screen.game.local_multiplayer.LocalMultiplayerGameScreen
import com.game.rockpaperscissors.presentation.screen.game.random.RandomGameScreen
import com.game.rockpaperscissors.data.GameModesEnum
import com.game.rockpaperscissors.data.local.database.GameDataEvent
import com.game.rockpaperscissors.presentation.screen.game.online_multiplayer.OnlineMultiplayerGameScreen
import com.game.rockpaperscissors.presentation.screen.game.online_multiplayer.OnlineMultiplayerGameViewModel

@Composable
fun SettingUpGameScreen(
    gameMode: GameModesEnum,
    gameViewModel: GameViewModel,
    onEvent: (GameDataEvent) -> Unit,
    navController: NavController,
    context: Context,
    onlineMultiplayerGameViewModel: OnlineMultiplayerGameViewModel
){

    when(gameMode){
        GameModesEnum.RANDOM -> {
            RandomGameScreen(
                gameViewModel = gameViewModel,
                onEvent = onEvent,
                navController = navController,
                context = context
            )
        }
        GameModesEnum.LOCAL_MULTIPLAYER -> {
            LocalMultiplayerGameScreen(
                gameViewModel = gameViewModel,
                onEvent = onEvent,
                navController = navController,
                context = context
            )
        }
        GameModesEnum.ONLINE_MULTIPLAYER -> {
            OnlineMultiplayerGameScreen(
                viewModel = onlineMultiplayerGameViewModel,
                gameViewModel = gameViewModel,
                onEvent = onEvent,
                navController = navController,
                context = context
            )
        }
        GameModesEnum.AI_MODE -> TODO()
    }
}



