package com.game.rockpaperscissors.presentation.auth

import android.net.Uri
import com.game.rockpaperscissors.presentation.auth.AuthViewModel
import com.game.rockpaperscissors.presentation.auth.MyAppUser
import com.game.rockpaperscissors.presentation.auth.UserRepository
import com.game.rockpaperscissors.presentation.auth.UserResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: UserRepository
) : AuthViewModel() {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")
    val userName = MutableStateFlow("")
    val profilePicUri: MutableStateFlow<Uri?> = MutableStateFlow(null)

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun updateUserName(newUserName: String) {
        userName.value = newUserName
    }

    fun updateProfilePic(newProfilePic: Uri) {
        profilePicUri.value = newProfilePic
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        confirmPassword.value = newConfirmPassword
    }

    fun onSignUpClick(goToScreen: () -> Unit, errorHandling: (UserResult<MyAppUser>) -> Unit) {
        launchCatching {
            if (password.value != confirmPassword.value) {
                throw Exception("Passwords do not match")
            }


            val res = accountService.signUpEMail(email.value, password.value)

            errorHandling(res)

            accountService.updateDisplayName(userName = userName.value)


            if(res is UserResult.Success) {
                goToScreen()
            }
        }
    }

}