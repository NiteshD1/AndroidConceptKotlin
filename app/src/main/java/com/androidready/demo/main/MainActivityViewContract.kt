package com.androidready.demo.main

import com.androidready.demo.data.models.Product

interface MainActivityViewContract {
    suspend fun displayProductList(productList:List<Product>?,isSavedProducts : Boolean = false)
}