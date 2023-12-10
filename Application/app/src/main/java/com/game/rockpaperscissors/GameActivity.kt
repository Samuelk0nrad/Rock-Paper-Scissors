package com.game.rockpaperscissors

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    Text(text = "1234")

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

var playerSelection = Selection()
var enemySelection = Selection()
var roundData = currentRoundData(
    playerSelection = 2,
    enemySelection = 2,
    isEnemySelect = false,
    isPlayerSelect = false
)

@Preview
@Composable
fun GameScreen(){

    roundData.playerSelection = playerSelection.currentSelection
    roundData.enemySelection = enemySelection.currentSelection

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
            Player(isReady = roundData.isEnemySelect)
            Spacer(modifier = Modifier.height(55.dp))
            enemySelection.isSelected = true
            enemySelection.currentSelection = 1
            enemySelection.Weapon(false)
        }

        //Vs. segment
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            VsPlayer()
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
            Player(isReady = roundData.isPlayerSelect)
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}