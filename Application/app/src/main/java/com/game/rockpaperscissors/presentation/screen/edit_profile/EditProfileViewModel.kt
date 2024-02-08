package com.game.rockpaperscissors.presentation.screen.edit_profile

import android.content.Context
import android.util.Log
import com.game.rockpaperscissors.presentation.auth.AuthViewModel
import com.game.rockpaperscissors.presentation.auth.UserRepository
import com.game.rockpaperscissors.presentation.auth.third_party_sign_in.UserData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    context: Context
) : AuthViewModel(context) {
    private val _userData: MutableStateFlow<UserData?> = MutableStateFlow(
        Firebase.auth.currentUser?.let {
            UserData(
                userId = it.uid,
                username = it.displayName,
                email = it.email,
                profilePictureUrl = it.photoUrl?.toString()
            )
        }

    )
    val userData = _userData.asStateFlow()

    val userName = MutableStateFlow("")
    val email = MutableStateFlow("")


    init {
        userName.update {
            _userData.value?.username ?: ""
        }
        email.update {
            _userData.value?.email ?: ""
        }
    }

    fun updateUserName(newUserName: String){
        userName.update {
            newUserName
        }
    }


    fun updateEmail(newEmail: String){
        email.update {
            newEmail
        }
    }

    fun onClickSave(goToScreen: () -> Unit, onValueChange: (Boolean) -> Unit = {}){
        launchCatching {
            onValueChange(true)

            if(email.value != _userData.value?.email){
                Log.d("currentUser", "email.Change")
                userRepository.updateEmail(email.value)
            }

            if(userName.value != _userData.value?.username){
                Log.d("currentUser", "userName.Change")

                userRepository.updateDisplayName(userName.value)
            }

            onValueChange(false)
            goToScreen()
        }
    }

    fun onClickChangePassword(onValueChange: (Boolean) -> Unit = {}){
        launchCatching {
            onValueChange(true)
            _userData.value?.email?.let { userRepository.resetPassword(it) }
            onValueChange(false)
        }
    }

}












