package com.game.rockpaperscissors

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.presentation.auth.AuthViewModel
import com.game.rockpaperscissors.presentation.auth.third_party_sign_in.GoogleAuthUiClient
import com.game.rockpaperscissors.presentation.screen.StartScreen
import com.game.rockpaperscissors.presentation.screen.WelcomeScreen
import com.game.rockpaperscissors.presentation.screen.reset_passwort.ResetPasswordScreen
import com.game.rockpaperscissors.presentation.screen.reset_passwort.ResetPasswordViewModel
import com.game.rockpaperscissors.presentation.screen.sign_in.SignInScreen
import com.game.rockpaperscissors.presentation.screen.sign_in.SignInViewModel
import com.game.rockpaperscissors.presentation.screen.sign_up.SignUpScreen
import com.game.rockpaperscissors.presentation.screen.sign_up.SignUpViewModel
import com.game.rockpaperscissors.ui.theme.RockPaperScissorsTheme
import com.game.rockpaperscissors.ui.theme.appColor
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpInActivity : ComponentActivity() {


    private lateinit var auth: FirebaseAuth


    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }



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
                        composable(route = Screen.MainActivity.route) {

                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)

                        }
                        composable(route = Screen.LoginScreen.route) {

                            val viewModel = hiltViewModel<SignInViewModel>()

                            SignInScreen(
                                navController = navController,
                                viewModel = viewModel,
                            )
                        }
                        composable(route = Screen.ResetPasswordScreen.route){
                            val viewModel = hiltViewModel<ResetPasswordViewModel>()

                            ResetPasswordScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }
                        composable(route = Screen.SignUpScreen.route) {

                            val viewModel = hiltViewModel<SignUpViewModel>()

                            SignUpScreen(
                                navController = navController,
                                auth = auth,
                                context = applicationContext,
                                viewModel = viewModel,
                            )

                        }
                        composable(route = Screen.WelcomeScreen.route) {


                            val googleSignInViewModel = viewModel<AuthViewModel>()
                            val state by googleSignInViewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit) {
                                if(googleAuthUiClient.getSignedInUser() != null) {
                                    navController.navigate("profile")
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if(result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            googleSignInViewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if(state.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Sign in successful",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.navigate("profile")
                                    googleSignInViewModel.resetState()
                                }
                            }

                            WelcomeScreen(
                                navController = navController,
                                onGoogleSignIn = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                },
                                context = applicationContext
                            )
                        }
                        composable(route = Screen.LogedAlreadyIn.route){

                            val user = auth.currentUser

                            Log.d("currentUser --","$user : user")
                            Log.d("currentUser --","${user?.isEmailVerified} : user")

                            if(user == null){
                                Toast.makeText(
                                    applicationContext,
                                    "Something went wrong",
                                    Toast.LENGTH_SHORT
                                ).show()

                                navController.navigate(Screen.WelcomeScreen.route)
                            } else{
                                navController.navigate(Screen.MainActivity.route)
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



