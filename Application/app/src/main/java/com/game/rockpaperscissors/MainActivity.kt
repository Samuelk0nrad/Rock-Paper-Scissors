package com.game.rockpaperscissors

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.game.rockpaperscissors.composable.Navigation
import com.game.rockpaperscissors.composable.screen.StatisticScreen
import com.game.rockpaperscissors.data.viewModel.TestViewModel
import com.game.rockpaperscissors.ui.theme.RockPaperScissorsTheme
import com.game.rockpaperscissors.ui.theme.appColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            RockPaperScissorsTheme {
                SetBarColor(colorSystem = appColor.background)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = appColor.background
                ) {
                    Navigation()
                }
            }
        }
    }
}


@Composable
fun SetBarColor(colorSystem: Color){
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(colorSystem)
    }
}


@Composable
fun SetBarColor(colorStatus: Color,colorNavigation: Color){
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setNavigationBarColor(colorNavigation)
        systemUiController.setStatusBarColor(colorStatus)
    }
}













