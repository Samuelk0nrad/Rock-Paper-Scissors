package com.game.rockpaperscissors.presentation.auth

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.util.Log
import com.game.rockpaperscissors.R
import com.game.rockpaperscissors.data.User
import com.game.rockpaperscissors.presentation.auth.third_party_sign_in.SignInResult
import com.game.rockpaperscissors.presentation.auth.third_party_sign_in.UserData
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import javax.inject.Inject


class FirebaseUserRepository @Inject constructor(
    val context: Context,
) : UserRepository {

    override val currentUser: UserData?
        get(){

            val user = Firebase.auth.currentUser
            val res = user?.let {
                UserData(
                    userId = user.uid,
                    username = user.displayName,
                    profilePictureUrl = user.photoUrl.toString()

                )
            }
            return res
        }

    override val signInResult: SignInResult = SignInResult(
        data = Firebase.auth.currentUser?.run {
            UserData(
                userId = uid,
                username = displayName,
                profilePictureUrl = photoUrl?.toString()
            )
        },
        errorMessage = null
    )

    override fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override suspend fun signInEMail(email: String, password: String) : SignInResult {
        return try {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            Log.d("currentUser", "Workt!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            val firebaseUser = Firebase.auth.currentUser ?: throw NullPointerException("User is null")
            SignInResult(
                data = firebaseUser.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )

        }
    }

    override suspend fun signUpEMail(email: String, password: String, userName: String, profilePicture: String?) : SignInResult {
        return try {
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            Log.d("currentUser", "Workt!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            val firebaseUser = Firebase.auth.currentUser ?: throw NullPointerException("User is null")

            //updateDisplayName(userName)



            //if(profilePicture != null)
            //    updateProfilePic(profilePicture)



            SignInResult(
                data = firebaseUser.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e

            e.message?.let { Log.d("currentUser --FUR", it) }
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    override suspend fun updateDisplayName(userName: String) {

        try {
            val profileUpdates = userProfileChangeRequest {
                displayName = userName
            }
            Firebase.auth.currentUser!!.updateProfile(profileUpdates).await()

        }catch (e: Exception){
            e.printStackTrace()
            e.message?.let { Log.d("currentUser --DNFUR", it) }

        }
    }

    override suspend fun updateProfilePic(userName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        Firebase.auth.signOut()
    }

    override suspend fun deleteAccount() {
        Firebase.auth.currentUser!!.delete().await()
    }

    // Convert FirebaseUser to your User model if needed
    private fun FirebaseUser.toMyAppUser(): MyAppUser {
        return MyAppUser(uid, email)
    }

}

