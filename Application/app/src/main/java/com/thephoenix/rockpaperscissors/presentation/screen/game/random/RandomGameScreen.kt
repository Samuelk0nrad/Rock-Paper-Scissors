package com.thephoenix.rockpaperscissors.presentation.screen.game.random

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.thephoenix.rockpaperscissors.presentation.screen.game.GameScreen
import com.thephoenix.rockpaperscissors.data.PlayerPlayData
import com.thephoenix.rockpaperscissors.data.SelectionType
import com.thephoenix.rockpaperscissors.data.local.database.GameDataEvent
import com.thephoenix.rockpaperscissors.presentation.screen.game.GameViewModel

@Composable
fun RandomGameScreen(
    gameViewModel: GameViewModel,
    onEvent: (GameDataEvent) -> Unit,
    navController: NavController,
    context: Context

){

    val gameComposable by remember {
        mutableStateOf(
            GameScreen(
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
    Log.d("Screen.GameScreen.route", "${gameComposable.enemyData.selection}")
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
                isSelected = false,
                isOnToSelect = true
            )
            enemyHide = true
            enemySelection = SelectionType.ROCK
            enemyIsSelected = false

            gameComposable.enemyData = PlayerPlayData(
                hide = enemyHide,
                isSelected = false,
                selection = enemySelection,
                isSelectable = false,
                isOnToSelect = false
            )
        },
        context = context
    )



    if (!enemyIsSelected) {
        enemySelection = gameComposable.currentRound.randomEnemySelection()
        enemyIsSelected = true
        gameComposable.setEnemySelection(true)
        gameComposable.setEnemySelection(enemySelection)

//        gameComposable.enemyData = gameComposable.enemyData.copy(
//            selection = enemySelection
//        )

        Log.d("Screen.GameScreen.route", "$enemySelection")
        Log.d("Screen.GameScreen.route", "${gameComposable.enemyData.selection}")

    }

    if(gameComposable.playerData.isSelected){
        enemyHide = false
        gameComposable.enemyData = gameComposable.enemyData.copy(hide = false)
        gameComposable.winner(
            onReset = {
                gameComposable.playerData = PlayerPlayData(
                    hide = false,
                    isSelectable = true,
                    selection = SelectionType.ROCK,
                    isSelected = false,
                    isOnToSelect = true
                )
                enemyHide = true
                enemySelection = SelectionType.ROCK
                enemyIsSelected = false

                gameComposable.enemyData = PlayerPlayData(
                    hide = enemyHide,
                    isSelected = false,
                    selection = enemySelection,
                    isSelectable = false,
                    isOnToSelect = false
                )
            }
        )
    }
}