package com.androidready.demo.data.api

import com.androidready.demo.data.models.Product
import retrofit2.Response
import retrofit2.http.GET

interface StoreApi {

        @GET("products")
        suspend fun getProductList(): Response<List<Product>>

}