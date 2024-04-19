package com.thephoenix.rockpaperscissors.data.local.database

sealed interface PlayerDataEvent{
    object CreateNewPlayer: PlayerDataEvent
    data class SetFullName(val fullName: String): PlayerDataEvent
    data class SetUserName(val userName: String): PlayerDataEvent
    data class SetBirthData(val birthData: String): PlayerDataEvent
    data class SetGender(val gender: String): PlayerDataEvent
    data class SetUserImage(val image: String): PlayerDataEvent
    data class SetShowName(val showName: Boolean): PlayerDataEvent
    data class SetShowData(val showData: Boolean): PlayerDataEvent
    data class DeletePlayer(val playerData: PlayerData): PlayerDataEvent
}