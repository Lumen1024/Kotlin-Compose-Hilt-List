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

// Автоматически подставляет классы в конструкторы помеченные @Inject constructor
@Module
@InstallIn(SingletonComponent::class) // Чтобы датабаза была синглтоном
object DatabaseModule {

    // Автоматическое создание датабазы
    @Singleton
    @Provides
    fun provideProductDatabase(
        @ApplicationContext app : Context
    ) = Room.databaseBuilder(
        app,
        ProductDatabase::class.java,
        "main_db"
    ).build()

    // Автоматическое создание Дао
    @Singleton
    @Provides
    fun provideProductDao(db : ProductDatabase) = db.dao
}