package com.game.rockpaperscissors.presentation.auth

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.util.Log
import com.game.rockpaperscissors.R
import com.game.rockpaperscissors.data.User
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
    val oneTapClient: SignInClient
) : UserRepository {

    override val currentUser: Flow<User?>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid) })
                }
            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
        }

    override val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty()

    override fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override suspend fun signInEMail(email: String, password: String) : UserResult<MyAppUser> {
        return try {
            val result = Firebase.auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user?.toMyAppUser() ?: throw NullPointerException("User is null")
            Log.d("currentUser", "Workt!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            UserResult.Success(user)
        } catch (e: Exception) {
            Log.d("currentUser", "loginUserEmailliukjgmnhgfb")
            UserResult.Error(e.localizedMessage ?: "Authentication failed.")

        }
    }

    override suspend fun signUpEMail(email: String, password: String) : UserResult<MyAppUser> {
        return try {
            val result = Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user?.toMyAppUser() ?: throw NullPointerException("User is null")
            Log.d("currentUser", "Workt!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            UserResult.Success(user)
        } catch (e: Exception) {
            Log.d("currentUser", "${e.localizedMessage}")

            UserResult.Error(e.localizedMessage ?: "Authentication failed.")
        }
    }

    override suspend fun signInGoogle(intent: Intent) {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)

        try {
            Firebase.auth.signInWithCredential(googleCredentials).await().user
        }catch (e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }

    override suspend fun signInGoogleIntent(): IntentSender?{
        val reslt = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            null

        }

        return reslt?.pendingIntent?.intentSender
    }
    private fun buildSignInRequest(): BeginSignInRequest{
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(context.getString(R.string.firebase_web_client_id))
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    override suspend fun updateDisplayName(userName: String) {
        val profileUpdates = userProfileChangeRequest {
            displayName = userName
        }

        Firebase.auth.currentUser!!.updateProfile(profileUpdates).await()
    }

    override suspend fun updateProfilePic(userName: Uri) {
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

