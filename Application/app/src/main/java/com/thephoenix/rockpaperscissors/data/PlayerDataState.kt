package com.thephoenix.rockpaperscissors.data

import com.thephoenix.rockpaperscissors.data.local.database.PlayerData

data class PlayerDataState(
    val allPlayer: List<PlayerData> = emptyList(),
    val fullName: String = "",
    val userName: String = "",
    val birthData: String = "",
    val gender: String = "---",
    val userImage: String = "",
    val showData: Boolean = true,
    val showName: Boolean = true
)
