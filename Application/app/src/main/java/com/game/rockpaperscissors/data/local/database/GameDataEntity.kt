package com.game.rockpaperscissors.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.game.rockpaperscissors.data.Enemy
import com.game.rockpaperscissors.data.GameModesEnum
import com.game.rockpaperscissors.data.OneRound
import com.game.rockpaperscissors.data.WinTyp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
class GameDataEntity (
    val mode: GameModesEnum,
    val rounds: Int,
    val win: WinTyp,

    @Embedded
    val allRounds: OneRound,
    @Embedded
    val enemy: Enemy,

    @ColumnInfo(name = "timestamp")
    val timestamp: LocalDateTime = LocalDateTime.now(),

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)

object LocalDateTimeConverter {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let {
            return LocalDateTime.parse(it, formatter)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.format(formatter)
    }
}