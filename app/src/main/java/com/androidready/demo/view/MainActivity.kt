package com.androidready.demo.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.androidready.demo.MainViewModel
import com.androidready.demo.Utils
import com.androidready.demo.controller.MainController
import com.androidready.demo.databinding.ActivityMainBinding
import com.androidready.demo.model.api.RetrofitInstance
import com.androidready.demo.model.db.room.ProductDatabase
import com.androidready.demo.model.models.Product
import com.androidready.demo.view.adapter.RecyclerViewAdapterForProducts
import kotlinx.coroutines.*
import java.io.*

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    var productList : List<Product>? = listOf<Product>()
    var savedProductList : List<Product>? = listOf<Product>()
    private lateinit var adapter : RecyclerViewAdapterForProducts
    private val controller = MainController()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        println("Activity : onCreate")
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        setupCounter()
        setupRecyclerView(productList)


        binding.buttonLoadAllProducts.setOnClickListener{
            binding.progressBar.visibility = View.VISIBLE
            GlobalScope.launch(Dispatchers.IO) {
                displayAllProductFromNetwork()
            }
        }

        binding.buttonLoadSavedProducts.setOnClickListener{
            binding.progressBar.visibility = View.VISIBLE
            GlobalScope.launch(Dispatchers.IO) {
                displaySavedProductFromDb()
            }
        }
    }

    private fun setupCounter() {
        GlobalScope.launch(Dispatchers.Main) {
            while (true){
                binding.textViewCounter.text = viewModel.getCountValue().toString()
                delay(1000)
            }
        }
    }

    suspend fun displaySavedProductFromDb() {
        withContext(Dispatchers.Main){
            binding.progressBar.visibility = View.GONE
            savedProductList = controller.getAllProductFromDb()
            setupRecyclerView(savedProductList,true)
        }
    }

    suspend fun displayAllProductFromNetwork() {
        withContext(Dispatchers.Main){
            binding.progressBar.visibility = View.GONE
            productList = controller.getAllProductFromServer()
            setupRecyclerView(productList)
        }
    }

    private fun setupRecyclerView(products: List<Product>?,isSavedProduct : Boolean = false) {

        adapter = RecyclerViewAdapterForProducts(
            products?.toMutableList() ?: mutableListOf(),
            isSavedProduct
        )

        // Setting the Adapter with the recyclerview
        binding.recyclerView.adapter = adapter
    }


    @SuppressLint("ResourceType")
    override fun onBackPressed() {
        //fragmentManager.popBackStack()


        super.onBackPressed()
    }



}


