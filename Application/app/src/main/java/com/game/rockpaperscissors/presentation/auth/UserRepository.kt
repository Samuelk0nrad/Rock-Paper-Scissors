package com.game.rockpaperscissors.presentation.auth

import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import com.game.rockpaperscissors.data.User
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    val currentUser: Flow<User?>
    val currentUserId: String
    fun hasUser(): Boolean
    suspend fun signInEMail(email: String, password: String) : UserResult<MyAppUser>
    suspend fun signUpEMail(email: String, password: String) : UserResult<MyAppUser>
    suspend fun signInGoogle(intent: Intent)
    suspend fun signInGoogleIntent() : IntentSender?
    suspend fun updateDisplayName(userName: String)
    suspend fun updateProfilePic(userName: Uri)
    suspend fun signOut()
    suspend fun deleteAccount()
}

