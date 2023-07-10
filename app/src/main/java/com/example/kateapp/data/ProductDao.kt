package com.example.kateapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

// Интерфейс для работы с датабазой
@Dao
interface ProductDao {

    // Добавление Продукта
    @Upsert
    suspend fun insertProduct(product: Product)

    // Удаление продукта
    @Delete
    suspend fun deleteProduct(product: Product)

    // Получение списка, сортированного по id
    @Query("SELECT * FROM products ORDER BY id")
    fun getProductsOrderById() : Flow<List<Product>>

    // Получение списка, сортированного по name
    @Query("SELECT * FROM products ORDER BY name")
    fun getProductsOrderByName() : Flow<List<Product>>

    // Получение списка, сортированного по price
    @Query("SELECT * FROM products ORDER BY price")
    fun getProductsOrderByPrice() : Flow<List<Product>>

    // Получение списка, сортированного по count
    @Query("SELECT * FROM products ORDER BY count")
    fun getProductsOrderByCount() : Flow<List<Product>>
}