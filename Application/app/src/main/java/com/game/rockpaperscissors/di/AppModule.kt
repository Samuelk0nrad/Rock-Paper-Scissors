package com.game.rockpaperscissors.di

import android.content.Context
import androidx.room.Room
import com.game.rockpaperscissors.data.local.database.GameDataDao
import com.game.rockpaperscissors.data.local.database.GameDatabase
import com.game.rockpaperscissors.data.local.database.PlayerData
import com.game.rockpaperscissors.data.local.database.PlayerDataDao
import com.game.rockpaperscissors.data.local.database.PlayerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePlayerDb(@ApplicationContext appContext: Context) : PlayerDatabase = Room.databaseBuilder(
        appContext,
        PlayerDatabase::class.java,
        "player.db"
    ).build()

    @Provides
    @Singleton
    fun providePlayerDBDao(playerDatabase: PlayerDatabase): PlayerDataDao = playerDatabase.dao

    @Provides
    @Singleton
    fun provideString(): String = "hallo"


    @Provides
    @Singleton
    fun provideGameDb(@ApplicationContext appContext: Context) : GameDatabase = Room.databaseBuilder(
        appContext,
        GameDatabase::class.java,
        "game.db"
    ).build()

    @Provides
    @Singleton
    fun provideGameDBDao(gameDatabase: GameDatabase): GameDataDao = gameDatabase.dao
}