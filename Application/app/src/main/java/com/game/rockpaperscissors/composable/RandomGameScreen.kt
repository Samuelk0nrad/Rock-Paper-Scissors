package com.game.rockpaperscissors.composable

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.game.rockpaperscissors.composable.screen.GameScreen
import com.game.rockpaperscissors.data.PlayerPlayData
import com.game.rockpaperscissors.data.SelectionType
import com.game.rockpaperscissors.data.local.database.GameDataEvent
import com.game.rockpaperscissors.data.viewModel.GameViewModel

@Composable
fun RandomGameScreen(
    gameViewModel: GameViewModel,
    onEvent: (GameDataEvent) -> Unit,
    navController: NavController

){

    val gameComposable by remember {
        mutableStateOf(GameScreen(
            gameViewModel = gameViewModel,
            onEvent = onEvent,
            navController = navController,
            )
        )
    }

    var enemyHide by remember {
        mutableStateOf(true)
    }

    var enemyIsSelected by remember {
        mutableStateOf(false)
    }

    var enemySelection by remember {
        mutableStateOf(SelectionType.ROCK)
    }

    Log.d("Screen.GameScreen.route", "update")
    gameComposable.printisReady()

    gameComposable.enemyData = gameComposable.enemyData.copy(
        hide = enemyHide,
        isSelectable = false,
        isSelected = enemyIsSelected,
        selection = enemySelection
    )

    gameComposable.playerData = gameComposable.playerData.copy(
        hide = false,
        isSelectable = true
    )

    gameComposable.CompGameScreen(
        onReset = {
            gameComposable.playerData = PlayerPlayData(
                hide = false,
                isSelectable = true,
                selection = SelectionType.ROCK,
                isSelected = false
            )
            enemyHide = true
            enemySelection = SelectionType.ROCK
            enemyIsSelected = false

            gameComposable.enemyData = PlayerPlayData(
                hide = enemyHide,
                isSelected = false,
                selection = enemySelection,
                isSelectable = false
            )
        }
    )



    if (!enemyIsSelected) {
        enemySelection = gameComposable.currentRound.randomEnemySelection()
        enemyIsSelected = true

    }

    if(gameComposable.playerData.isSelected){
        gameComposable.enemyData = gameComposable.enemyData.copy(hide = false)
        gameComposable.winner(
            onReset = {
                gameComposable.playerData = PlayerPlayData(
                    hide = false,
                    isSelectable = true,
                    selection = SelectionType.ROCK,
                    isSelected = false
                )
                enemyHide = true
                enemySelection = SelectionType.ROCK
                enemyIsSelected = false

                gameComposable.enemyData = PlayerPlayData(
                    hide = enemyHide,
                    isSelected = false,
                    selection = enemySelection,
                    isSelectable = false
                )
            }
        )
    }
}