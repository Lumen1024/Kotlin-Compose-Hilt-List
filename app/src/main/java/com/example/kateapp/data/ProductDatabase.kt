package com.example.kateapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    // Описание всех таблиц, которые будут созданы
    entities =[Product::class],
    version = 1
)
abstract class ProductDatabase : RoomDatabase() {
    abstract val dao : ProductDao
}