package com.thephoenix.rockpaperscissors.presentation.screen.reset_passwort

import android.content.Context
import com.thephoenix.rockpaperscissors.presentation.auth.AuthViewModel
import com.thephoenix.rockpaperscissors.presentation.auth.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val accountService: UserRepository,
    context: Context
): AuthViewModel(context) {
    val email = MutableStateFlow("")

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun onSignInClick(goToScreen: () -> Unit, errorHandling: (String?) -> Unit, onValueChange: (Boolean) -> Unit = {}) {
        launchCatching {

            onValueChange(true)

            val res = accountService.resetPassword(email.value)
            onValueChange(false)

            errorHandling(res.errorMessage)


            if(res.errorMessage == null) {
                goToScreen()
            }
        }
    }


}