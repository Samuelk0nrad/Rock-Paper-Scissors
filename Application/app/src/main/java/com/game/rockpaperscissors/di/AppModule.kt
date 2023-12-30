package com.game.rockpaperscissors.di

import android.content.Context
import androidx.room.Room
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
}