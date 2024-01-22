package com.game.rockpaperscissors.data.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.rockpaperscissors.firebase.UserResult
import com.game.rockpaperscissors.firebase.MyAppUser
import com.game.rockpaperscissors.firebase.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FirebaseViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {


    private val _loginResult = MutableLiveData<UserResult<MyAppUser>>()
    val loginResult: LiveData<UserResult<MyAppUser>> = _loginResult

    fun loginUserEmail(email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = userRepository.loginUserEmail(email, password)
                _loginResult.value = when (result) {
                    is UserResult.Success -> UserResult.Success(result.data)
                    is UserResult.Error -> UserResult.Error(result.errorMessage)
                    else -> {
                        UserResult.Error("Unexpected Error")
                    }
                }
            } catch (e: Exception) {
                _loginResult.value = UserResult.Error(e.localizedMessage ?: "Unexpected error occurred.")
                Log.d("currentUser", "Ellasdlfkj")
            }
        }
    }

    fun signUpUserEmail(email: String, password: String){
        viewModelScope.launch {
            try {
                val result = userRepository.signUpUserEmail(email, password)
                _loginResult.value = when (result) {
                    is UserResult.Success -> UserResult.Success(result.data)
                    is UserResult.Error -> UserResult.Error(result.errorMessage)
                    else -> {
                        UserResult.Error("Unexpected Error")
                    }
                }
            } catch (e: Exception) {
                Log.d("currentUser", "Ellasdlfkj")
                _loginResult.value = UserResult.Error(e.localizedMessage ?: "Unexpected error occurred.")
            }
        }
    }
}





