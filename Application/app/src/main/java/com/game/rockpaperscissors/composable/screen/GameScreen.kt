package com.game.rockpaperscissors.composable.screen

import android.os.Looper
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.game.rockpaperscissors.GameFunktions
import com.game.rockpaperscissors.composable.Player
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.composable.Selection
import com.game.rockpaperscissors.composable.VsPlayer
import com.game.rockpaperscissors.data.CurrentRoundData
import com.game.rockpaperscissors.data.GameData
import com.game.rockpaperscissors.data.GameDataState
import com.game.rockpaperscissors.data.OneRound
import com.game.rockpaperscissors.data.PlayerDataState
import com.game.rockpaperscissors.data.SelectionType
import com.game.rockpaperscissors.data.WinTyp
import com.game.rockpaperscissors.data.local.database.GameDataEvent
import com.game.rockpaperscissors.data.viewModel.GameViewModel
import com.game.rockpaperscissors.ui.theme.Oswald
import com.game.rockpaperscissors.ui.theme.appColor


var currentRound = GameFunktions()

var playerSelection by mutableStateOf(Selection())
var enemySelection by mutableStateOf(Selection())
var roundData by mutableStateOf(
    CurrentRoundData(
        playerSelection = SelectionType.ROCK,
        enemySelection = SelectionType.ROCK,
        isEnemySelect = false,
        isPlayerSelect = false
    )
)

var isShowWinText by mutableStateOf(false)
var winText: String = ""

var statistics by mutableStateOf(
    GameData(
        playerWins = 0,
        enemyWins = 0,
        rounds = 3,
        currentRound = 1,
    )
)

var isReset = true

fun onStart(
    gameViewModel: GameViewModel,
    onEvent: (GameDataEvent) -> Unit
){
    statistics.rounds = gameViewModel.rounds.value
    onEvent(GameDataEvent.SetRounds(gameViewModel.rounds.value))
}

@Composable
fun GameScreen(
    navController: NavController,
    gameViewModel: GameViewModel,
    playerState: PlayerDataState,
    state: GameDataState,
    onEvent: (GameDataEvent) -> Unit
){
    roundData.playerSelection = playerSelection.currentSelection

    var isVisible by remember {
        mutableStateOf(isShowWinText)
    }

    onStart(gameViewModel, onEvent)

    isVisible = isShowWinText


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor.background)
    ){


        //Enemy
        Column (
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(60.dp))
            Player(isReady = enemySelection.isSelected)
            Spacer(modifier = Modifier.height(55.dp))

            enemySelection.Weapon(
                isSelectable = false,
                hide = !playerSelection.isSelected
            )
        }

        //Player
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.rotate(180f)
            ){
                playerSelection.Weapon()
            }
            Spacer(modifier = Modifier.height(55.dp))
            Player(
                isReady = playerSelection.isSelected,
                level = playerState.allPlayer[0].level,
                userName = playerState.allPlayer[0].userName,
            )
            Spacer(modifier = Modifier.height(60.dp))
        }

        //Vs. segment
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            VsPlayer(
                playerWins = statistics.playerWins,
                enemyWins = statistics.enemyWins,
                rounds = statistics.rounds,
                currentRound = statistics.currentRound
            )
        }


        AnimatedVisibility(
            visible = isVisible,
            modifier = Modifier
                .fillMaxSize()
                .background(appColor.background.copy(alpha = 0.7f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        //isShowWinText = false
                        if (!isReset) {
                            reset(navController, onEvent)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = winText,
                    fontSize = 90.sp,
                    color = when(winText){
                        "Win" -> Color.Green
                        "Lose" -> Color.Red
                        else -> Color.Gray
                    },
                    letterSpacing = 3.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Bold,

                    )
            }
        }
    }

    randomEnemySelection(navController, gameViewModel, onEvent)
}


var isSet:Boolean = false
fun randomEnemySelection(
    navController: NavController,
    gameViewModel: GameViewModel,
    onEvent: (GameDataEvent) -> Unit
){
    if(!isSet) {
        enemySelection.currentSelection = currentRound.randomEnemySelection()
        enemySelection.isSelected = true
        isSet = true
    }
    winner(navController, gameViewModel, onEvent)
}

var isWaiting = false
private val handler = android.os.Handler(Looper.getMainLooper())
fun winner(
    navController: NavController,
    gameViewModel: GameViewModel,
    onEvent: (GameDataEvent) -> Unit
){
    if(isReset){
        handler.removeCallbacksAndMessages(null)
        Log.d("Restarafdsavt","handler to null")
    }
    if(enemySelection.isSelected && playerSelection.isSelected && !isWaiting){
        isReset = false

        currentRound.EnemySelection = enemySelection.currentSelection
        currentRound.YourSelection = playerSelection.currentSelection



        when(enemySelection.currentSelection){
            SelectionType.PAPER -> gameViewModel.updateEnemySelectionPaper()
            SelectionType.ROCK -> gameViewModel.updateEnemySelectionRock()
            SelectionType.SCISSORS -> gameViewModel.updateEnemySelectionScissors()
        }


        when(playerSelection.currentSelection){
            SelectionType.PAPER -> gameViewModel.updatePlayerSelectionPaper()
            SelectionType.ROCK -> gameViewModel.updatePlayerSelectionRock()
            SelectionType.SCISSORS -> gameViewModel.updatePlayerSelectionScissors()
        }



        val win = currentRound.winner()

        when (win) {
            WinTyp.Win -> {
                statistics.playerWins++
                statistics.currentRound++
                gameViewModel.updateWin()
            }
            WinTyp.Lose -> {
                statistics.enemyWins++
                statistics.currentRound++
                gameViewModel.updateLose()
            }
            WinTyp.Draw -> {
                gameViewModel.updateDraw()
            }
        }

        when {
            statistics.enemyWins == statistics.playerWins -> {
                onEvent(GameDataEvent.SetWin(WinTyp.Draw))
            }
            statistics.enemyWins < statistics.playerWins -> {
                onEvent(GameDataEvent.SetWin(WinTyp.Win))
            }
            else -> {
                onEvent(GameDataEvent.SetWin(WinTyp.Lose))
            }
        }

        winText = "$win"

        val delayMillis: Long = 3000 // Adjust the delay time as needed (in milliseconds)
        isWaiting = true
        isShowWinText = true

        statistics.rounds = gameViewModel.rounds.value

        if(statistics.currentRound <= statistics.rounds){

            handler.postDelayed({
                if(!isReset) {
                    reset(navController, onEvent)

                    Log.d("Restarafdsavt","in Time")
                }
            }, delayMillis)
        }
        val oneRound = OneRound(
            enemySelection = currentRound.EnemySelection!!,
            playerSelection = currentRound.YourSelection!!,
            result = win

        )
        onEvent(GameDataEvent.SetNewRound(oneRound))
    }
}

fun reset(
    navController: NavController,
    onEvent: (GameDataEvent) -> Unit
) {
    if(statistics.currentRound <= statistics.rounds) {

        Log.d("Restarafdsavt", "reset")
        isWaiting = false
        isSet = false
        isShowWinText = false
        isReset = true
        currentRound = GameFunktions()
        enemySelection = Selection()
        playerSelection.currentSelection = SelectionType.ROCK
        playerSelection.isSelected = false
        roundData = CurrentRoundData(
            playerSelection = SelectionType.ROCK,
            enemySelection = SelectionType.ROCK,
            isEnemySelect = false,
            isPlayerSelect = false
        )
    }else{
        endGame(navController, onEvent)
    }
}

fun endGame(
    navController: NavController,
    onEvent: (GameDataEvent) -> Unit
){
//Game ReSet//
    Log.d("Restarafdsavt", "reset")
    isWaiting = false
    isSet = false
    isShowWinText = false
    isReset = true
    currentRound = GameFunktions()
    enemySelection = Selection()
    playerSelection.currentSelection = SelectionType.ROCK
    playerSelection.isSelected = false
    roundData = CurrentRoundData(
        playerSelection = SelectionType.ROCK,
        enemySelection = SelectionType.ROCK,
        isEnemySelect = false,
        isPlayerSelect = false
    )

    navController.popBackStack()
    navController.navigate(Screen.GameStatisticScreen.route)
    onEvent(GameDataEvent.CreateNewPlayer)

    statistics = GameData(
        playerWins = 0,
        enemyWins = 0,
        rounds = 3,
        currentRound = 1,
    )
//^Game Reset^//
}