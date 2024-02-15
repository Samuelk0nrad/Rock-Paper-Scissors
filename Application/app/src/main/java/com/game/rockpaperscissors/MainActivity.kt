package com.game.rockpaperscissors

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
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


        Log.d("currentUser", "Main Activity ${auth.currentUser?.uid}")

        requestNotificationPermission()


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
//                    Greeting(name = "asdf")

                }
            }
        }
    }


    private fun requestNotificationPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if(!hasPermission) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
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




@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var requestId by remember { mutableStateOf(0) }
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data("https://cataas.com/cat")
        .memoryCachePolicy(CachePolicy.DISABLED)
        .diskCachePolicy(CachePolicy.DISABLED)
        .setParameter("requestId", requestId, memoryCacheKey = null)
        .build()

    AsyncImage(
        model = imageRequest,
        contentDescription = "",
        modifier = Modifier.clickable(onClick = {
            requestId++
        })
    )
}








