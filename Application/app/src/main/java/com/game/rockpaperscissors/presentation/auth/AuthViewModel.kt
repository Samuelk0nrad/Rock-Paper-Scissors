package com.game.rockpaperscissors.presentation.auth

import android.content.ComponentCallbacks
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.rockpaperscissors.R
import com.game.rockpaperscissors.presentation.auth.third_party_sign_in.SignInResult
import com.game.rockpaperscissors.presentation.auth.third_party_sign_in.SignInState
import com.game.rockpaperscissors.presentation.auth.third_party_sign_in.UserData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
open class AuthViewModel @Inject constructor(
    context: Context
) : ViewModel() {

    private val TAG = "Auth"

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    fun resetState() {
        _state.update { SignInState() }
    }

    fun launchCatching(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                Log.d(ERROR_TAG, throwable.message.orEmpty())
            },
            block = block
        )


    companion object {
        const val ERROR_TAG = "NOTES APP ERROR"
    }

    private val database: DatabaseReference = Firebase.database(context.getString(R.string.firebase_realtime_database)).reference
    fun uploadUserData(userData: UserData){
        Log.d(TAG, "uploading User Data")


        val userRef = database.child("users").child(userData.username ?: "noName")


        userRef.get()
            .addOnSuccessListener {
                if(!it.exists()){
                    userRef.child("userId").setValue(userData.userId)
                    userRef.child("profilePicture").setValue(userData.profilePictureUrl)
                    userRef.child("email").setValue(userData.email)
                }
            }
    }
}













