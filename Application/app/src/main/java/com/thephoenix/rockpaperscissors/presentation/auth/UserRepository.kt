package com.thephoenix.rockpaperscissors.presentation.auth

import com.thephoenix.rockpaperscissors.presentation.auth.third_party_sign_in.SignInResult
import com.thephoenix.rockpaperscissors.presentation.auth.third_party_sign_in.UserData


interface UserRepository {
    val currentUser: UserData?
    val signInResult: SignInResult
    fun hasUser(): Boolean
    suspend fun signInEMail(email: String, password: String) : SignInResult
    suspend fun signUpEMail(email: String, password: String, userName: String, profilePicture: String?) : SignInResult
    suspend fun updateDisplayName(userName: String)
    suspend fun updateEmail(email: String)
    suspend fun updateProfilePic(profilePicture: String)
    suspend fun resetPassword(email: String) : SignInResult
    suspend fun deleteUser(): SignInResult
    suspend fun signOut()
    suspend fun deleteAccount()
}

