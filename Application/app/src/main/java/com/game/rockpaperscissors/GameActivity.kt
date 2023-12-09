package com.game.rockpaperscissors

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.game.rockpaperscissors.ui.theme.RockPaperScissorsTheme

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent{
            RockPaperScissorsTheme {

            }
        }
    }
}

@Preview
@Composable
fun GameScreen(){

}



