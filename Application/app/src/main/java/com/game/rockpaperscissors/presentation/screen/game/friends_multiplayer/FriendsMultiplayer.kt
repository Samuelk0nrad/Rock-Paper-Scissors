package com.game.rockpaperscissors.presentation.screen.game.friends_multiplayer

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.game.rockpaperscissors.data.PlayerPlayData
import com.game.rockpaperscissors.data.SelectionType
import com.game.rockpaperscissors.data.local.database.GameDataEvent
import com.game.rockpaperscissors.presentation.auth.third_party_sign_in.UserData
import com.game.rockpaperscissors.presentation.screen.game.GameScreen
import com.game.rockpaperscissors.presentation.screen.game.GameViewModel


@Composable
fun FriendsMultiplayer(
    viewModel: FriendsMultiplayerViewModel,
    userName: String,
    gameViewModel: GameViewModel,
    onEvent: (GameDataEvent) -> Unit,
    navController: NavController,
    context: Context
) {

    var hasFunctionBeenCalled by remember {
        mutableStateOf(false)
    }

    var hasGameStart by remember {
        mutableStateOf(false)
    }

    if(!hasFunctionBeenCalled){
        viewModel.startGame(gameViewModel.rounds.collectAsState().value, userName){
            hasGameStart = it
        }
        hasFunctionBeenCalled = true
    }

    gameViewModel.updateEnemy(viewModel.enemy.collectAsState().value
        ?: UserData(
            userId = "",
            username = "Noname",
            profilePictureUrl = null,
            email = null
        )
    )




        val gameComposable by remember {
            mutableStateOf(
                GameScreen(
                    gameViewModel = gameViewModel,
                    onEvent = onEvent,
                    navController = navController,
                ) {
                    viewModel.endGame()
                }
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

        var playerUpdate by remember {
            mutableStateOf(false)
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

        if (!playerUpdate && gameComposable.playerData.isSelected) {
            viewModel.updatePlayerState(true, gameComposable.playerData.selection)
            playerUpdate = true
        } else if (!gameComposable.playerData.isSelected) {
            playerUpdate = false
        }


        fun resetGame(){
            viewModel.updateRound()
            viewModel.getEnemyState()
            gameComposable.playerData = PlayerPlayData(
                hide = false,
                isSelectable = true,
                selection = SelectionType.ROCK,
                isSelected = false,
                isOnToSelect = true
            )
            enemyHide = true
            enemySelection = SelectionType.ROCK
            gameComposable.setEnemySelection(SelectionType.ROCK)
            gameComposable.setEnemySelection(false)
            enemyIsSelected = false
            gameComposable.enemyData = PlayerPlayData(
                hide = true,
                isSelected = false,
                selection = SelectionType.ROCK,
                isSelectable = false,
                isOnToSelect = false
            )
        }

        gameComposable.CompGameScreen(
            onReset = {
                resetGame()
            },
            context = context
        )

        gameComposable.setEnemySelection(enemyIsSelected)
        gameComposable.setEnemySelection(enemySelection)



        if (gameComposable.playerData.isSelected && gameComposable.enemyData.isSelected) {
            enemyHide = false
            gameComposable.enemyData = gameComposable.enemyData.copy(hide = false)
            gameComposable.winner {
                resetGame()
            }
        } else {
            enemyIsSelected = viewModel.enemyState.collectAsState().value.isReady
            enemySelection =
                viewModel.enemyState.collectAsState().value.selection ?: SelectionType.ROCK
        }



}