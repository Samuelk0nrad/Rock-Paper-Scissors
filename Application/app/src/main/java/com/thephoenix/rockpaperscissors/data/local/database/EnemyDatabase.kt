package com.thephoenix.rockpaperscissors.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PlayerData::class],
    version = 1,
    exportSchema = true,
)
abstract class EnemyDatabase : RoomDatabase() {
    abstract val dao: PlayerDataDao
}