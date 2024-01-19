package com.game.rockpaperscissors.firebase




interface UserRepository {
    suspend fun loginUserEmail(email: String, password: String) : UserResult<MyAppUser>
    suspend fun signUpUserEmail(email: String, password: String) : UserResult<MyAppUser>
}

