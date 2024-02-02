package com.game.rockpaperscissors.presentation.auth

import android.net.Uri
import com.game.rockpaperscissors.data.User
import com.game.rockpaperscissors.presentation.auth.third_party_sign_in.SignInResult
import com.game.rockpaperscissors.presentation.auth.third_party_sign_in.UserData
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    val currentUser: UserData?
    val signInResult: SignInResult
    fun hasUser(): Boolean
    suspend fun signInEMail(email: String, password: String) : SignInResult
    suspend fun signUpEMail(email: String, password: String, userName: String, profilePicture: String?) : SignInResult
    suspend fun updateDisplayName(userName: String)
    suspend fun updateEmail(email: String)
    suspend fun updateProfilePic(userName: String)
    suspend fun resetPassword(email: String) : SignInResult
    suspend fun deleteUser(): SignInResult
    suspend fun signOut()
    suspend fun deleteAccount()
}

