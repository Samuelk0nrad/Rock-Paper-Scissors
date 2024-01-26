package com.game.rockpaperscissors.presentation.auth

import androidx.activity.result.IntentSenderRequest
import com.game.rockpaperscissors.presentation.auth.AuthViewModel
import com.game.rockpaperscissors.presentation.auth.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThirdPartySignIn @Inject constructor(
    private val accountService: UserRepository
) : AuthViewModel() {


    fun onSignInGoogleClick(goToScreen: () -> Unit) {

        launchCatching {
            val signInIntentSender = accountService.signInGoogleIntent()

            IntentSenderRequest.Builder(
                signInIntentSender ?: return@launchCatching
            ).build()


            goToScreen()

        }
    }


    fun onSignInXClick(goToScreen: () -> Unit) {
        TODO()
    }


    fun onSignInGithubClick(goToScreen: () -> Unit) {
        TODO()
    }


    fun onSignInAppleClick(goToScreen: () -> Unit) {
        TODO()
    }

}











