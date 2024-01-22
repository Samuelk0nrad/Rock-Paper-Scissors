package com.game.rockpaperscissors.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FirebaseUserRepository @Inject constructor(
    private val auth: FirebaseAuth
) : UserRepository {

    override suspend fun loginUserEmail(email: String, password: String) : UserResult<MyAppUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user?.toMyAppUser() ?: throw NullPointerException("User is null")
            Log.d("currentUser", "Workt!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            UserResult.Success(user)
        } catch (e: Exception) {
            Log.d("currentUser", "loginUserEmailliukjgmnhgfb")
            UserResult.Error(e.localizedMessage ?: "Authentication failed.")

        }
    }

    override suspend fun signUpUserEmail(email: String, password: String) : UserResult<MyAppUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user?.toMyAppUser() ?: throw NullPointerException("User is null")
            Log.d("currentUser", "Workt!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            UserResult.Success(user)
        } catch (e: Exception) {
            Log.d("currentUser", "${e.localizedMessage}")

            UserResult.Error(e.localizedMessage ?: "Authentication failed.")



        }
    }

    // Convert FirebaseUser to your User model if needed
    private fun FirebaseUser.toMyAppUser(): MyAppUser {
        return MyAppUser(uid, email)
    }

}

sealed class UserResult<out T> {
    data class Success<out T>(val data: T) : UserResult<T>()
    object UnitSuccess : UserResult<Nothing>()
    data class Error(val errorMessage: String) : UserResult<Nothing>()
}

data class MyAppUser(
    val uid: String?,
    val email: String?,
    // other properties you may need
)
