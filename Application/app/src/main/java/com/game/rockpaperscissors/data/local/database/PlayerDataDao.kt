package com.game.rockpaperscissors.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDataDao {
    @Insert
    suspend fun upsertPlayer(playerData: PlayerData)

    @Delete
    suspend fun deletePlayer(playerData: PlayerData)


    @Query("SELECT * FROM playerdata")
    fun allPlayerData(): Flow<List<PlayerData>>
}