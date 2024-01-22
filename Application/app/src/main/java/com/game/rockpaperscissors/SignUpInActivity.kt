package com.game.rockpaperscissors

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.game.rockpaperscissors.composable.screen.CreateProfileScreen
import com.game.rockpaperscissors.composable.screen.LoginScreen
import com.game.rockpaperscissors.composable.screen.SignUpScreen
import com.game.rockpaperscissors.composable.screen.StartScreen
import com.game.rockpaperscissors.composable.screen.VerificationScreen
import com.game.rockpaperscissors.composable.screen.WelcomeScreen
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.data.viewModel.FirebaseViewModel
import com.game.rockpaperscissors.data.viewModel.PlayerViewModel
import com.game.rockpaperscissors.ui.theme.RockPaperScissorsTheme
import com.game.rockpaperscissors.ui.theme.appColor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpInActivity : ComponentActivity() {


    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!this::auth.isInitialized){
            auth = Firebase.auth
        }
        

        Log.d("currentUser", "RockPaperScissorsTheme")
        setContent {
            RockPaperScissorsTheme {
                val navController = rememberNavController()


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = appColor.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.StartScreen.route
                    ) {
                        composable(route = Screen.StartScreen.route) {
                            SetBarColor(appColor.background)
                            StartScreen(navController)
                        }
                        composable(route = Screen.CreateNewAccountScreen.route) {
                            SetBarColor(colorSystem = appColor.background)
                            val viewModel = hiltViewModel<PlayerViewModel>()
                            val state by viewModel.state.collectAsState()
                            CreateProfileScreen(
                                state = state,
                                onEvent = viewModel::onEvent,
                                nevController = navController,
                                context = applicationContext,
                                auth.currentUser
                            )
                        }
                        composable(route = Screen.LoginScreen.route) {

                            val firebaseViewModel by viewModels<FirebaseViewModel>()




                            LoginScreen(
                                navController = navController,
                                firebaseViewModel::loginUserEmail
                            )
                        }
                        composable(route = Screen.SignUpScreen.route) {

                            val firebaseViewModel by viewModels<FirebaseViewModel>()



                            SignUpScreen(
                                navController = navController,
                                auth = auth,
                                context = applicationContext,
                                firebaseViewModel = firebaseViewModel
                            )
                        }
                        composable(route = Screen.WelcomeScreen.route) {
                            WelcomeScreen(
                                navController = navController
                            )
                        }
                        composable(route = Screen.LogedAlreadyIn.route){

                            val user = auth.currentUser

                            Log.d("currentUser","$user : user")
                            Log.d("currentUser","${user?.isEmailVerified} : user")

                            if(user == null){
                                Toast.makeText(
                                    applicationContext,
                                    "Something went wrong",
                                    Toast.LENGTH_SHORT
                                ).show()

                                navController.navigate(Screen.WelcomeScreen.route)
                            }else if(!user.isEmailVerified){

                                user.sendEmailVerification()
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Log.d("currentUser", "Email sent.")
                                        }
                                    }

                                VerificationScreen(
                                    reSendEmail = {
                                        user.sendEmailVerification()
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    Log.d("currentUser", "Email sent.")
                                                }
                                            }
                                    },
                                    verifyLater = {
                                        navController.navigate(Screen.CreateNewAccountScreen.route)
                                    }
                                )
                            } else{
                                navController.navigate(Screen.CreateNewAccountScreen.route)
                            }
                        }
                    }
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        if(!this::auth.isInitialized){
            auth = Firebase.auth
        }
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.d("currentUser", "isSign In $currentUser")
        }
        else{
            Log.d("currentUser", "NOT isSign In")
        }
    }
}



