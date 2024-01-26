package com.game.rockpaperscissors.presentation.auth

import android.net.Uri
import android.util.Log
import com.game.rockpaperscissors.presentation.auth.AuthViewModel
import com.game.rockpaperscissors.presentation.auth.MyAppUser
import com.game.rockpaperscissors.presentation.auth.UserRepository
import com.game.rockpaperscissors.presentation.auth.UserResult
import com.game.rockpaperscissors.presentation.auth.third_party_sign_in.SignInResult
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

    fun onSignUpClick(goToScreen: () -> Unit, errorHandling: (String?) -> Unit, onValueChange: (Boolean) -> Unit = {}) {
        launchCatching {
            onValueChange(true)

            var errorMessage: String? = null


            if (password.value != confirmPassword.value) {
                errorMessage = "Passwords do not match"
                errorHandling(errorMessage)
                onValueChange(false)
            }


            val res = accountService.signUpEMail(email.value, password.value,userName.value,null)

            res.errorMessage?.let { Log.d("currentUser --SUVM", it) }

            accountService.updateDisplayName(userName = userName.value)
            onValueChange(false)



            if(errorMessage == null) {
                errorMessage = res.errorMessage
            }

            errorHandling(errorMessage)

            if(res.errorMessage == null) {
                goToScreen()
            }
        }
    }

}