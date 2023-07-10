package com.example.kateapp.di

import android.content.Context
import androidx.room.Room
import com.example.kateapp.data.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideProductDatabase(
        @ApplicationContext app : Context
    ) = Room.databaseBuilder(
        app,
        ProductDatabase::class.java,
        "main_db"
    ).build()

    @Singleton
    @Provides
    fun provideProductDao(db : ProductDatabase) = db.dao
}