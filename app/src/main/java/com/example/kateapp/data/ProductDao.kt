package com.example.kateapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Upsert
    suspend fun insertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT * FROM products ORDER BY id")
    fun getProductsOrderById() : Flow<List<Product>>

    @Query("SELECT * FROM products ORDER BY name")
    fun getProductsOrderByName() : Flow<List<Product>>

    @Query("SELECT * FROM products ORDER BY price")
    fun getProductsOrderByPrice() : Flow<List<Product>>

    @Query("SELECT * FROM products ORDER BY count")
    fun getProductsOrderByCount() : Flow<List<Product>>
}