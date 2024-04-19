package com.thephoenix.rockpaperscissors.presentation.auth.third_party_sign_in

import android.app.Activity
import android.content.Context
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider

class ThirdPartySignInProvider(
    private val context: Context,
    private val oneTapClient: SignInClient
) {

    private lateinit var firebaseAuth: FirebaseAuth

    fun twitter() {
        val provider = OAuthProvider.newBuilder("twitter.com")
    }

    fun getPendingAuthResult() {
        // [START auth_oidc_pending_result]
        val pendingResultTask = firebaseAuth.pendingAuthResult

    }

    fun signInWithProvider(provider: OAuthProvider.Builder) {

        firebaseAuth
            .startActivityForSignInWithProvider(context as Activity, provider.build())
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }

    }
}