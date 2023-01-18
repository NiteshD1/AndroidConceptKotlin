package com.androidready.demo.main

import android.view.View
import com.androidready.demo.Utils
import com.androidready.demo.data.api.RetrofitInstance
import com.androidready.demo.data.db.room.ProductDatabase
import com.androidready.demo.data.models.Product

class MainActivityPresenter(val mainView: MainActivityViewContract?): MainActivityPresenterContract{

   // var mainView: MainActivityViewContract? = null
    val dao = ProductDatabase.getInstance()?.getProductDao()
    val api = RetrofitInstance.api

    override suspend fun fetchAllProductsFromServer() {
        var responseProductList = api.getProductList()
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
        mainView?.displayProductList(productList,false) ?: Utils.showToastOnMainThread("Facing Some Error!")
    }

    override suspend fun fetchAllSavedProductsFromDb() {
        val productList = dao?.getAllProducts()
        mainView?.displayProductList(productList,true) ?: Utils.showToastOnMainThread("Facing Some Error!")
    }

    override suspend fun addProductIntoDb(product: Product) {
        if(dao?.getById(product.id) == null){
            dao?.upsert(product)
            Utils.showToastOnMainThread("Product Saved!")
        }else{
            Utils.showToastOnMainThread("Product Already Saved!")
        }
    }

    override suspend fun removeProductFromDb(product: Product) {
        dao?.deleteProduct(product)

        Utils.showToastOnMainThread("Product Deleted!")
    }


}