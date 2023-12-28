package com.game.rockpaperscissors.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlayerData(
    val name: String,
    val userName: String,
    val birthData: String,
    val gender: String,
    val userImage: String,
    val showName: Boolean,
    val showData: Boolean,
    val level: Int = 0,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)




