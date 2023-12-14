package com.game.rockpaperscissors

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.game.rockpaperscissors.data.currentRoundData
import com.game.rockpaperscissors.ui.theme.RockPaperScissorsTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController


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

val currentRound = GameFunktions()

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



@Preview
@Composable
fun GameScreen(

){
    roundData.playerSelection = playerSelection.currentSelection

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

        //Vs. segment
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            VsPlayer(
                playerWins = currentRound.wins,
                enemyWins = currentRound.loses
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
            Player(isReady = playerSelection.isSelected)
            Spacer(modifier = Modifier.height(60.dp))
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

fun winner(){
    if(enemySelection.isSelected && playerSelection.isSelected){
        currentRound.EnemySelection = enemySelection.currentSelection
        currentRound.YourSelection = playerSelection.currentSelection

    }
}





