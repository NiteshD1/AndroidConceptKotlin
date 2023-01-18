package com.androidready.demo.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.androidready.demo.databinding.ActivityMainBinding
import com.androidready.demo.data.models.Product
import kotlinx.coroutines.*
import java.io.*

class MainActivity : AppCompatActivity(),MainActivityViewContract,DbCallback{

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter : RecyclerViewAdapterForProducts

    private var presenter: MainActivityPresenterContract? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        println("Activity : onCreate")
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        presenter = MainActivityPresenter(this)
        //setupRecyclerView(listOf())


        binding.buttonLoadAllProducts.setOnClickListener{
            binding.progressBar.visibility = View.VISIBLE
            GlobalScope.launch(Dispatchers.IO) {
                (presenter as MainActivityPresenter).fetchAllProductsFromServer()
            }
        }

        binding.buttonLoadSavedProducts.setOnClickListener{
            binding.progressBar.visibility = View.VISIBLE
            GlobalScope.launch(Dispatchers.IO) {
                (presenter as MainActivityPresenter).fetchAllSavedProductsFromDb()
            }
        }
    }



    private fun setupRecyclerView(products: List<Product>?,isSavedProduct : Boolean = false) {

        adapter = RecyclerViewAdapterForProducts(
            products?.toMutableList() ?: mutableListOf(),
            isSavedProduct,
            this
        )

        // Setting the Adapter with the recyclerview
        binding.recyclerView.adapter = adapter
    }


    @SuppressLint("ResourceType")
    override fun onBackPressed() {
        //fragmentManager.popBackStack()


        super.onBackPressed()
    }

    override suspend fun displayProductList(productList: List<Product>?, isSavedProducts: Boolean) {

        withContext(Dispatchers.Main){
            binding.progressBar.visibility = View.GONE
            setupRecyclerView(productList,isSavedProducts)
        }
    }

    override suspend fun addProductToDb(product: Product) {
        presenter?.addProductIntoDb(product)
    }

    override suspend fun removeProductFromDb(product: Product) {
        presenter?.removeProductFromDb(product)
    }
}


