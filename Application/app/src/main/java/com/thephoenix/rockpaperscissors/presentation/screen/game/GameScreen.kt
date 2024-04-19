package com.thephoenix.rockpaperscissors.presentation.screen.game

import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.thephoenix.rockpaperscissors.GameFunktions
import com.thephoenix.rockpaperscissors.R
import com.thephoenix.rockpaperscissors.composable.Player
import com.thephoenix.rockpaperscissors.data.Screen
import com.thephoenix.rockpaperscissors.composable.Selection
import com.thephoenix.rockpaperscissors.composable.VsPlayer
import com.thephoenix.rockpaperscissors.data.CurrentRoundData
import com.thephoenix.rockpaperscissors.data.GameData
import com.thephoenix.rockpaperscissors.data.OneRound
import com.thephoenix.rockpaperscissors.data.PlayerPlayData
import com.thephoenix.rockpaperscissors.data.SelectionType
import com.thephoenix.rockpaperscissors.data.WinTyp
import com.thephoenix.rockpaperscissors.data.local.database.GameDataEvent
import com.thephoenix.rockpaperscissors.ui.theme.Oswald
import com.thephoenix.rockpaperscissors.ui.theme.appColor


class GameScreen (
    private val gameViewModel: GameViewModel,
    private val onEvent: (GameDataEvent) -> Unit,
    private val navController: NavController,
    private val GameEnd: () -> Unit = {}
){

    var currentRound = GameFunktions()

    private var _playerSelection by mutableStateOf(Selection())
    private var _enemySelection by mutableStateOf(Selection())



    private var _playerData by mutableStateOf(
        PlayerPlayData(
        selection = SelectionType.ROCK,
        isSelected = false,
        isSelectable = true,
        hide = false,
        isOnToSelect = true
    )
    )

    private var _enemyData by mutableStateOf(
        PlayerPlayData(
        selection = SelectionType.ROCK,
        isSelected = false,
        isSelectable = true,
        hide = false,
        isOnToSelect = false
    )
    )

    var playerData get() = _playerData
        set(value) {_playerData = value}

    var enemyData get() = _enemyData
        set(value) {_enemyData = value}


    private var roundData by mutableStateOf(
        CurrentRoundData(
            playerSelection = SelectionType.ROCK,
            enemySelection = SelectionType.ROCK,
            isEnemySelect = false,
            isPlayerSelect = false
        )
    )

    private var isShowWinText by mutableStateOf(false)
    private var winText: String = ""

    private var statistics by mutableStateOf(
        GameData(
            playerWins = 0,
            enemyWins = 0,
            rounds = 3,
            currentRound = 1,
        )
    )

    private var isReset = true

    init {
        statistics.rounds = gameViewModel.rounds.value
        onEvent(GameDataEvent.SetRounds(gameViewModel.rounds.value))
    }



    @Composable
    fun CompGameScreen(
        onReset: () -> Unit,
        context: Context
    ) {
        roundData.playerSelection = _playerSelection.currentSelection
        roundData.enemySelection = _enemySelection.currentSelection

        var isVisible by remember {
            mutableStateOf(isShowWinText)
        }

        var enemyBackground by remember {
            mutableIntStateOf(R.drawable.rot_farb_verlauf)
        }
        enemyBackground = if(_enemyData.isOnToSelect) R.drawable.gruen_farb_verlauf else R.drawable.rot_farb_verlauf


        var playerBackground by remember {
            mutableIntStateOf(R.drawable.gruen_farb_verlauf)
        }

        playerBackground = if(_playerData.isOnToSelect) R.drawable.gruen_farb_verlauf else R.drawable.rot_farb_verlauf

        isVisible = isShowWinText

        val player = gameViewModel.player
        val enemy = gameViewModel.enemy


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(appColor.background)
        ) {


            Box(
                modifier = Modifier
                    .fillMaxSize()
            ){
                Image(
                    modifier = Modifier.rotate(180f)
                        .fillMaxWidth(),
                    painter = painterResource(id = enemyBackground),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
//                    alpha = 0.5f
                )

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ){
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(id = playerBackground),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
//                        alpha = 0.5f
                    )

                }
            }

            //Enemy
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                Player(
                    isReady = _enemyData.isSelected,
                    userName = enemy.value?.username ?: "",
                    level = 0,
                    isBot = true,
                    onClick = {
                        gameViewModel.selectedPlayer = gameViewModel.enemy.value

                        navController.navigate(Screen.GamePlayerProfileScreen.route)
                    },
                    profilePicture = gameViewModel.enemy.value?.profilePictureUrl ?:""
                )
                Spacer(modifier = Modifier.height(55.dp))

                _enemySelection.Weapon(
                    isSelectable = _enemyData.isSelectable,
                    currentSelection = _enemyData.selection,
                    hide = _enemyData.hide,
                    onClick = {
                        _enemyData = _enemyData.copy(
                            isSelected = _enemySelection.isSelected,
                            selection = _enemySelection.currentSelection
                        )
                    }
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
                ) {
                    _playerSelection.Weapon(
                        isSelectable = _playerData.isSelectable,
                        currentSelection = _playerData.selection,
                        hide = _playerData.hide,
                        onClick = {
                            _playerData = _playerData.copy(
                                isSelected = _playerSelection.isSelected,
                                selection = _playerSelection.currentSelection
                            )
                        }
                    )
                }
                Spacer(modifier = Modifier.height(55.dp))
                Player(
                    isReady = _playerSelection.isSelected,
                    level = 0,
                    userName = player.value?.username ?:"",
                    onClick = {
                        gameViewModel.selectedPlayer = gameViewModel.player.value

                        navController.navigate(Screen.GamePlayerProfileScreen.route)
                    },
                    isBot = false,
                    profilePicture = gameViewModel.player.value?.profilePictureUrl ?:""
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
                                reset(onReset)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = winText,
                        fontSize = 90.sp,
                        color = when (winText) {
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
    }

    fun printisReady(){
        Log.d("Screen.GameScreen.route","${_enemySelection.currentSelection}")
    }


    private var isSet: Boolean = false

    private var isWaiting = false
    private val handler = android.os.Handler(Looper.getMainLooper())

    private var isRoundAdded = true

    fun winner(onReset: () -> Unit) {
        if (isReset) {
            handler.removeCallbacksAndMessages(null)
            Log.d("Restarafdsavt", "handler to null")
        }
        if (!isWaiting) {
            isReset = false

            currentRound.EnemySelection = _enemySelection.currentSelection
            currentRound.YourSelection = _playerSelection.currentSelection

            Log.d("GameSelection", "currentRound enemy Selection: ${currentRound.EnemySelection}, ${_enemyData.selection}")
            Log.d("GameSelection", "currentRound player Selection: ${currentRound.YourSelection}, ${_playerData.selection}")

            when (_enemySelection.currentSelection) {
                SelectionType.PAPER -> gameViewModel.updateEnemySelectionPaper()
                SelectionType.ROCK -> gameViewModel.updateEnemySelectionRock()
                SelectionType.SCISSORS -> gameViewModel.updateEnemySelectionScissors()
            }


            when (_playerSelection.currentSelection) {
                SelectionType.PAPER -> gameViewModel.updatePlayerSelectionPaper()
                SelectionType.ROCK -> gameViewModel.updatePlayerSelectionRock()
                SelectionType.SCISSORS -> gameViewModel.updatePlayerSelectionScissors()
            }


            val win = currentRound.winner()



            when (win) {
                WinTyp.Win -> {
                    statistics.playerWins++
                    gameViewModel.updateWin()
                    isRoundAdded = true
                }

                WinTyp.Lose -> {
                    statistics.enemyWins++
                    gameViewModel.updateLose()
                    isRoundAdded = true
                }

                WinTyp.Draw -> {
                    gameViewModel.updateDraw()
                    isRoundAdded = false
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

            if (statistics.currentRound <= statistics.rounds) {

                handler.postDelayed({
                    if (!isReset) {
                        reset(onReset)

                        Log.d("Restarafdsavt", "in Time")
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



    private fun reset(onReset: () -> Unit) {
        if(isRoundAdded)
            statistics.currentRound++
        if (statistics.currentRound <= statistics.rounds) {


            Log.d("Restarafdsavt", "reset")
            isWaiting = false
            isSet = false
            isShowWinText = false
            isReset = true
            currentRound = GameFunktions()
            _enemySelection = Selection()
            _playerSelection.currentSelection = SelectionType.ROCK
            _playerSelection.isSelected = false
            roundData = CurrentRoundData(
                playerSelection = SelectionType.ROCK,
                enemySelection = SelectionType.ROCK,
                isEnemySelect = false,
                isPlayerSelect = false
            )
            onReset()
        } else{
            statistics.currentRound--
            endGame()
        }
    }

    private fun endGame() {

        GameEnd()
        navController.popBackStack()
        navController.navigate(Screen.GameStatisticScreen.route)
        onEvent(GameDataEvent.CreateNewPlayer)
    }

    fun setEnemySelection(selection: SelectionType){
        _enemySelection.currentSelection = selection
    }
    fun setPlayerSelection(selection: SelectionType){
        _playerSelection.currentSelection = selection
    }

    fun setEnemySelection(isSelection: Boolean){
        _enemySelection.isSelected = isSelection
    }
    fun setPlayerSelection(isSelection: Boolean){
        _enemySelection.isSelected = isSelection
    }
}














