package com.thephoenix.rockpaperscissors.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.google.gson.Gson

@Database(
    entities = [GameDataEntity::class],
    version = 1
)
@TypeConverters(LocalDateTimeConverter::class, AllRoundConverter::class)
abstract class GameDatabase: RoomDatabase() {
    abstract val dao: GameDataDao

    companion object {
        val gson = Gson()
    }

}