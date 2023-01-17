package com.androidready.demo.controller

import android.view.View
import com.androidready.demo.Utils
import com.androidready.demo.model.api.RetrofitInstance
import com.androidready.demo.model.db.room.ProductDatabase
import com.androidready.demo.model.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainController {

    val dao = ProductDatabase.getInstance()?.getProductDao()

    suspend fun addProductToDb(product: Product){
        if(dao?.getById(product.id) == null){
            dao?.upsert(product)
            Utils.showToastOnMainThread("Product Saved!")
        }else{
            Utils.showToastOnMainThread("Product Already Saved!")
        }
    }

    suspend fun removeProductFromDb(product: Product){
        dao?.deleteProduct(product)

        Utils.showToastOnMainThread("Product Deleted!")
    }

    suspend fun getAllProductFromDb(): List<Product>?{
        return dao?.getAllProducts()
    }

    suspend fun getAllProductFromServer(): List<Product>?{
        var responseProductList = RetrofitInstance.api.getProductList()
        var productList: List<Product>? = listOf()

        if(responseProductList.isSuccessful){
            responseProductList.body().let { listOfProducts ->
                println("Product Data : ${listOfProducts.toString()}")
                listOfProducts.let {
                    productList = listOfProducts
                }
            }
        }else{
            println("Product List could not be fetched" + responseProductList.errorBody().toString())
        }
        return productList
    }
}