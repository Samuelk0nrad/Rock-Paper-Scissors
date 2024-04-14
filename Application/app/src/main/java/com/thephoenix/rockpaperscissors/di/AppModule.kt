package com.thephoenix.rockpaperscissors.di

import android.content.Context
import androidx.room.Room
import com.thephoenix.rockpaperscissors.data.local.database.EnemyDatabase
import com.thephoenix.rockpaperscissors.data.local.database.GameDataDao
import com.thephoenix.rockpaperscissors.data.local.database.GameDatabase
import com.thephoenix.rockpaperscissors.data.local.database.PlayerDataDao
import com.thephoenix.rockpaperscissors.data.local.database.PlayerDatabase
import com.thephoenix.rockpaperscissors.presentation.auth.FirebaseUserRepository
import com.thephoenix.rockpaperscissors.presentation.auth.UserRepository
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
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
    @Named("playerDao")
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

    @Provides
    @Singleton
    fun provideEnemyDb(@ApplicationContext appContext: Context) : EnemyDatabase = Room.databaseBuilder(
        appContext,
        EnemyDatabase::class.java,
        "enemy.db"
    ).createFromAsset("database/enemy.db").build()

    @Provides
    @Singleton
    @Named("enemyDao")
    fun provideEnemyDbDao(enemyDatabase: EnemyDatabase) : PlayerDataDao = enemyDatabase.dao

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context) : Context = appContext



    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseUserRepository(@ApplicationContext context: Context): FirebaseUserRepository {
        return FirebaseUserRepository(context)
    }


    @Provides
    @Singleton
    fun provideUserRepository(@ApplicationContext context: Context): UserRepository {
        return FirebaseUserRepository(context)
    }

    @Provides
    @Singleton
    fun provideSignInClient(@ApplicationContext context: Context): SignInClient = Identity.getSignInClient(context)


}