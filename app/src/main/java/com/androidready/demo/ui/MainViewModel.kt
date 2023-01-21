package com.androidready.demo.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidready.demo.Resource
import com.androidready.demo.Utils
import com.androidready.demo.data.models.Product
import com.androidready.demo.data.repository.MainRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel: ViewModel(){

    private val repository = MainRepository()
    var productResponseResource : MutableLiveData<Resource<List<Product>?>> = MutableLiveData()

    fun addProductToDb(product: Product) = viewModelScope.launch {
        repository.addProductIntoDb(product)
        Utils.showToastOnMainThread("Product Saved!")
    }

    fun removeProductFromDb(product: Product) = viewModelScope.launch {
        repository.removeProductFromDb(product)
        Utils.showToastOnMainThread("Product Deleted!")
    }

    fun getSavedProductFromDb() = repository.fetchAllSavedProductsFromDb()

    fun getAllProductsFromServer() = viewModelScope.launch {
        productResponseResource.postValue(Resource.Loading())

        val productListResponse = repository.fetchAllProductsFromServer()

        if(productListResponse.isSuccessful){
            productListResponse.body().let {
                productResponseResource.postValue(Resource.Success(it))
            }
        }else{
            productResponseResource.postValue(Resource.Error("Error while fetching data!"))
        }

    }
}