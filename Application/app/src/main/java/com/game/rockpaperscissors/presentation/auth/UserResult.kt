package com.game.rockpaperscissors.presentation.auth

sealed class UserResult<out T> {
    data class Success<out T>(val data: T) : UserResult<T>()
    object UnitSuccess : UserResult<Nothing>()
    data class Error(val errorMessage: String) : UserResult<Nothing>()
}