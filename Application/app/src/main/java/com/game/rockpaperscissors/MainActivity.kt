package com.game.rockpaperscissors

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.game.rockpaperscissors.composable.Navigation
import com.game.rockpaperscissors.data.viewModel.NavigationViewModel
import com.game.rockpaperscissors.ui.theme.RockPaperScissorsTheme
import com.game.rockpaperscissors.ui.theme.appColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<NavigationViewModel>()


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth


        installSplashScreen().apply {



            setKeepOnScreenCondition{


                if(auth.currentUser == null){
                    val intent = Intent(applicationContext, SignUpInActivity::class.java)
                    startActivity(intent)
                }

                false
            }
        }


        Log.d("currentUser", "Main Activity")


        setContent {
            RockPaperScissorsTheme {

                Log.d("currentUser", "Main Activity")
                SetBarColor(colorSystem = appColor.background)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = appColor.background
                ) {
                    Navigation(context = applicationContext)
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













