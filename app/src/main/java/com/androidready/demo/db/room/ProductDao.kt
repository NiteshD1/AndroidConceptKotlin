package com.androidready.demo.db.room

import androidx.room.*
import com.androidready.demo.models.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(product: Product)

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): MutableList<Product>

    @Delete()
    suspend fun deleteProduct(product: Product)

    @Query("DELETE FROM products")
    suspend fun deleteAll()

    @Query("DELETE FROM products WHERE id = :productId")
    suspend fun deleteById(productId : Int)


}