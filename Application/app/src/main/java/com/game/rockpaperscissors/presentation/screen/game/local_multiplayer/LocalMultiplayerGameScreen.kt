package com.game.rockpaperscissors.presentation.screen.game.local_multiplayer

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.game.rockpaperscissors.presentation.screen.game.GameScreen
import com.game.rockpaperscissors.data.PlayerPlayData
import com.game.rockpaperscissors.data.SelectionType
import com.game.rockpaperscissors.data.local.database.GameDataEvent
import com.game.rockpaperscissors.presentation.screen.game.GameViewModel

@Composable
fun LocalMultiplayerGameScreen(
    gameViewModel: GameViewModel,
    onEvent: (GameDataEvent) -> Unit,
    navController: NavController,
    context: Context
) {

    val gameComposable by remember {
        mutableStateOf(
            GameScreen(
                gameViewModel = gameViewModel,
                onEvent = onEvent,
                navController = navController,
            )
        )
    }


    var toEnemy by remember {
        mutableStateOf(false)
    }

    var toPlayer by remember {
        mutableStateOf(true)
    }



    if (toPlayer) {

        gameComposable.playerData = gameComposable.playerData.copy(
            hide = false,
            isSelectable = true,
            isOnToSelect = true
        )

        gameComposable.enemyData =  gameComposable.enemyData.copy(
            hide = true,
            isSelectable = false,
            isOnToSelect = false
        )

        if (gameComposable.playerData.isSelected) {
            toPlayer = false
            toEnemy = true
        }

    }


    if (toEnemy) {
        gameComposable.enemyData =  gameComposable.enemyData.copy(
            hide = false,
            isSelectable = true,
            isOnToSelect = true
        )
        gameComposable.playerData = gameComposable.playerData.copy(
            hide = true,
            isSelectable = false,
            isOnToSelect = false
        )


        if (gameComposable.playerData.isSelected) {
            toEnemy = false
            toPlayer = true
        }
    }




    gameComposable.CompGameScreen(
        onReset = {
            gameComposable.playerData = PlayerPlayData(
                hide = false,
                isSelectable = true,
                selection = SelectionType.ROCK,
                isSelected = false,
                isOnToSelect = true
            )

            gameComposable.enemyData = PlayerPlayData(
                hide = true,
                isSelected = false,
                selection = SelectionType.ROCK,
                isSelectable = false,
                isOnToSelect = false
            )
        },
        context = context
    )


    if(gameComposable.playerData.isSelected && gameComposable.enemyData.isSelected) {

        gameComposable.playerData = gameComposable.playerData.copy(
            hide = false
        )
        gameComposable.winner(
            onReset = {
                gameComposable.playerData = PlayerPlayData(
                    hide = false,
                    isSelectable = true,
                    selection = SelectionType.ROCK,
                    isSelected = false,
                    isOnToSelect = true
                )

                gameComposable.enemyData = PlayerPlayData(
                    hide = true,
                    isSelected = false,
                    selection = SelectionType.ROCK,
                    isSelectable = false,
                    isOnToSelect = false
                )
            },
        )
    }
}









