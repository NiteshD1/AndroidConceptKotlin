package com.androidready.demo.api

import com.androidready.demo.models.Product
import retrofit2.Response
import retrofit2.http.GET

interface StoreApi {

        @GET("products")
        suspend fun getProductList(): Response<List<Product>>

}