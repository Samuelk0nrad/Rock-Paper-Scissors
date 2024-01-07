package com.game.rockpaperscissors.composable

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.game.rockpaperscissors.data.GameModesEnum
import com.game.rockpaperscissors.data.local.database.GameDataEvent
import com.game.rockpaperscissors.data.viewModel.GameViewModel

@Composable
fun SetingUpGameScreen(
    gameMode: GameModesEnum,
    gameViewModel: GameViewModel,
    onEvent: (GameDataEvent) -> Unit,
    navController: NavController,
    context: Context
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
        GameModesEnum.LOCAL_MULTIPLAYER -> TODO()
        GameModesEnum.ONLINE_MULTIPLAYER -> TODO()
        GameModesEnum.AI_MODE -> TODO()
    }
}



