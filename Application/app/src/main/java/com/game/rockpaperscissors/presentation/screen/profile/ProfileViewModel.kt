package com.game.rockpaperscissors.presentation.screen.profile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.game.rockpaperscissors.presentation.auth.AuthViewModel
import com.game.rockpaperscissors.presentation.auth.FirebaseUserRepository
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
class ProfileViewModel @Inject constructor(

    private val userRepository: UserRepository

): AuthViewModel() {


    private val _userData: MutableStateFlow<UserData?> = MutableStateFlow(null)
    val userData = _userData.asStateFlow()


    fun updateUserData(){
        val user = Firebase.auth.currentUser
        _userData.update {
            user?.let { it1 ->
                Log.d("currentUser ------","${it1.email}")
                UserData(
                    userId = it1.uid,
                    email = it1.email,
                    username = it1.displayName,
                    profilePictureUrl = it1.photoUrl?.toString()
                )
            }
        }
    }

    fun onClickSignOut(goToScreen: () -> Unit, onValueChange: (Boolean) -> Unit = {}){
        launchCatching {
            onValueChange(true)
            userRepository.signOut()
            onValueChange(false)
            goToScreen()
        }

    }

    fun onClickDeleteAccount(goToScreen: () -> Unit, onValueChange: (Boolean) -> Unit = {}){
        launchCatching {
            onValueChange(true)
            Log.d("currentUser ---", "deleteAccount on work")
            userRepository.deleteAccount()
            Log.d("currentUser ---", "deleteAccount finish")
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