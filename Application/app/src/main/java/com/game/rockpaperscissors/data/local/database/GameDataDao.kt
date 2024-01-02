package com.game.rockpaperscissors.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDataDao {

    @Upsert
    suspend fun upsertGameData(gameDataEntity: GameDataEntity)

    @Delete
    suspend fun deleteGameData(gameDataEntity: GameDataEntity)

    @Query("SELECT * FROM GameDataEntity ORDER BY timestamp DESC")
    fun getGameDataOrderedByDate(): Flow<List<GameDataEntity>>
}