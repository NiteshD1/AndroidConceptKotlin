package com.androidready.demo.main

import com.androidready.demo.data.models.Product

interface DbCallback {
    suspend fun addProductToDb(product: Product)
    suspend fun removeProductFromDb(product: Product)
}