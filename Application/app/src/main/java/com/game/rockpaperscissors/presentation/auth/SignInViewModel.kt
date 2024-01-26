package com.game.rockpaperscissors.presentation.auth

import com.game.rockpaperscissors.presentation.auth.AuthViewModel
import com.game.rockpaperscissors.presentation.auth.MyAppUser
import com.game.rockpaperscissors.presentation.auth.UserRepository
import com.game.rockpaperscissors.presentation.auth.UserResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountService: UserRepository
) : AuthViewModel() {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun onSignUpClick(goToScreen: () -> Unit, errorHandling: (UserResult<MyAppUser>) -> Unit) {
        launchCatching {
            val res = accountService.signInEMail(email.value, password.value)

            errorHandling(res)

            if(res is UserResult.Success) {
                goToScreen()
            }
        }
    }
}