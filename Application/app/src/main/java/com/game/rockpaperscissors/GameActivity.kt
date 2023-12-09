package com.game.rockpaperscissors

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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