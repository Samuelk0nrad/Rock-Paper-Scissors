package com.game.rockpaperscissors.presentation.screen.sign_in


import android.content.ComponentCallbacks
import android.content.Context
import android.util.Log
import com.game.rockpaperscissors.R
import com.game.rockpaperscissors.presentation.auth.AuthViewModel
import com.game.rockpaperscissors.presentation.auth.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountService: UserRepository,
    context: Context
) : AuthViewModel(context) {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun onSignInClick(goToScreen: () -> Unit, errorHandling: (String?) -> Unit, onValueChange: (Boolean) -> Unit = {}) {
        launchCatching {

            onValueChange(true)

            if(!ifEmail(email.value)){
                getEmail{ email, error ->
                    if(email != null){
                        launchCatching {
                            val res = accountService.signInEMail(email, password.value)
                            onValueChange(false)

                            errorHandling(res.errorMessage)


                            if(res.errorMessage == null) {
                                goToScreen()
                            }
                        }

                    }else if (error != null){
                        errorHandling(error)
                        onValueChange(false)

                        throw Exception(error)

                    }
                }
            }else{
                val res = accountService.signInEMail(email.value, password.value)
                onValueChange(false)

                errorHandling(res.errorMessage)


                if(res.errorMessage == null) {
                    goToScreen()
                }

            }

        }
    }

    private val database = Firebase.database(context.getString(R.string.firebase_realtime_database)).reference

    fun getEmail(callback: (String?, String?) -> Unit){
        val userRef = database.child("users").child(email.value)


        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    val email = dataSnapshot.getValue(String::class.java)
                    callback(email, null)
                    return
                }else{
                    callback(null, "The supplied auth credential is incorrect, malformed or has expired.")
                    return
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                callback(null, "The supplied auth credential is incorrect, malformed or has expired.")
                return
            }
        }

        // Add the ValueEventListener to the reference
        userRef.addValueEventListener(valueEventListener)
    }

    fun ifEmail(email: String): Boolean = '@' in email
}