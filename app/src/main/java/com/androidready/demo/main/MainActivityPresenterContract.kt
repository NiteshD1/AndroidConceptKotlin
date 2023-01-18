package com.androidready.demo.main

import com.androidready.demo.data.models.Product

interface MainActivityPresenterContract {
    suspend fun fetchAllProductsFromServer()
    suspend fun fetchAllSavedProductsFromDb()
    suspend fun addProductIntoDb(product: Product)
    suspend fun removeProductFromDb(product: Product)
}