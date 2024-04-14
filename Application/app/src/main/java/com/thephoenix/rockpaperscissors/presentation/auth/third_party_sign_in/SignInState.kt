package com.thephoenix.rockpaperscissors.presentation.auth.third_party_sign_in

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)