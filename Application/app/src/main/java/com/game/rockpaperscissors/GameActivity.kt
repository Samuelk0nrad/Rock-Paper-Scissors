package com.game.rockpaperscissors

import android.content.Context
import android.content.Intent
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.game.rockpaperscissors.data.currentRoundData
import com.game.rockpaperscissors.data.GameData
import com.game.rockpaperscissors.ui.theme.RockPaperScissorsTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import font.Oswald
import kotlinx.coroutines.delay
import java.util.logging.Handler


class GameActivity : ComponentActivity() {

    override fun onStart() {
        super.onStart()
        setContent{
            RockPaperScissorsTheme {
                SetBarColor(color = MaterialTheme.colorScheme.background)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameScreen()
                }
            }
        }
    }

    @Composable
    private fun SetBarColor(color: Color){
        val systemUiController = rememberSystemUiController()

        SideEffect {
            systemUiController.setSystemBarsColor(color)
        }
    }
}

var currentRound = GameFunktions()

var playerSelection by mutableStateOf(Selection())
var enemySelection by mutableStateOf(Selection())
var roundData by mutableStateOf(
    currentRoundData(
        playerSelection = 2,
        enemySelection = 2,
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
        currentRound = 0,
    )
)

var isReset = true

@Preview
@Composable
fun GameScreen(){
    roundData.playerSelection = playerSelection.currentSelection


    var isVisible by remember {
        mutableStateOf(isShowWinText)
    }

    isVisible = isShowWinText


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
            Player(isReady = playerSelection.isSelected,
                level = if(isReset)1 else 0)
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
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.7f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        isShowWinText = false
                        if (!isReset) {
                            reset()
                            Log.d("Restarafdsavt","by Click")
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

    randomEnemySelection()
}


var isSet:Boolean = false
fun randomEnemySelection(){
    if(!isSet) {
        enemySelection.currentSelection = currentRound.randomEnemySelection()
        enemySelection.isSelected = true
        isSet = true
    }
    winner()
}

var isWaiting = false
private val handler = android.os.Handler(Looper.getMainLooper())
fun winner(){
    if(isReset){
        handler.removeCallbacksAndMessages(null)
        Log.d("Restarafdsavt","handler to null")
    }
    if(enemySelection.isSelected && playerSelection.isSelected && !isWaiting){
        isReset = false

        currentRound.EnemySelection = enemySelection.currentSelection
        currentRound.YourSelection = playerSelection.currentSelection

        val win = currentRound.Winer()

        if (win == "Win") {
            statistics.playerWins++
            statistics.currentRound++
        }
        else if (win == "Lose") {
            statistics.enemyWins++
            statistics.currentRound++
        }



        winText = win

        var delayMillis: Long = 3000 // Adjust the delay time as needed (in milliseconds)
        isWaiting = true
        isShowWinText = true

        handler.postDelayed({
            if(!isReset) {
                reset()

                Log.d("Restarafdsavt","in Time")
            }
        }, delayMillis)
    }
}

fun reset() {
    Log.d("Restarafdsavt","reset")
    isWaiting = false
    isSet = false
    isShowWinText = false
    isReset = true
    currentRound = GameFunktions()
    enemySelection = Selection()
    playerSelection.currentSelection = 2
    playerSelection.isSelected = false
    roundData = currentRoundData(
        playerSelection = 2,
        enemySelection = 2,
        isEnemySelect = false,
        isPlayerSelect = false
    )
}