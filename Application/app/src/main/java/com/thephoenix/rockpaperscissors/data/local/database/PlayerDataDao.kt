package com.thephoenix.rockpaperscissors.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDataDao {
    @Upsert
    suspend fun upsertPlayer(playerData: PlayerData)

    @Delete
    suspend fun deletePlayer(playerData: PlayerData)


    @Query("SELECT * FROM PlayerData")
    fun allPlayerData(): Flow<List<PlayerData>>

    @Query("SELECT COUNT(*) FROM PlayerData")
    suspend fun getCount(): Int
}