package com.thephoenix.rockpaperscissors.data

import androidx.room.Entity

@Entity
class Enemy(
    val playerId: Long,
    val fullName: String,
    val userName: String,
    val level: Int,
    val birthData: String,
    val gender: String,
    val userImage: String,
)
