package com.thephoenix.rockpaperscissors.presentation.auth.third_party_sign_in

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    var userId: String,
    var email: String?,
    val username: String?,
    val profilePictureUrl: String?
)
