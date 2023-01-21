package com.androidready.demo.data.repository

import com.androidready.demo.Utils
import com.androidready.demo.data.api.RetrofitInstance
import com.androidready.demo.data.db.room.ProductDatabase
import com.androidready.demo.data.models.Product

class MainRepository {
    val dao = ProductDatabase.getInstance()?.getProductDao()
    val api = RetrofitInstance.api

    suspend fun fetchAllProductsFromServer() = api.getProductList()

    fun fetchAllSavedProductsFromDb() = dao?.getAllProducts()

    suspend fun addProductIntoDb(product: Product) = dao?.upsert(product)

    suspend fun removeProductFromDb(product: Product) = dao?.deleteProduct(product)

}