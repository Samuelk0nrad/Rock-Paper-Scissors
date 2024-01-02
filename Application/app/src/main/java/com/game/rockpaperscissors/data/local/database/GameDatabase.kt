package com.game.rockpaperscissors.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [GameDataEntity::class],
    version = 1
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class GameDatabase: RoomDatabase() {
    abstract val dao: GameDataDao

}