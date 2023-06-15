package com.androidready.demo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.androidready.demo.Resource
import com.androidready.demo.Utils
import com.androidready.demo.databinding.ActivityMainBinding
import com.androidready.demo.data.models.Product
import kotlinx.coroutines.*
import java.io.*

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter : RecyclerViewAdapterForProducts

    val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        println("Activity : onCreate")
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        //setupRecyclerView(listOf())

        viewModel.productResponseResource.observe(this, Observer {
            when(it){
                is Resource.Success -> {
                    hideProgressBar()
                    setupRecyclerView(it.data)
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Utils.showToast(it.message.toString())
                }
                is Resource.Loading -> showProgressBar()
            }
        })


        binding.buttonLoadAllProducts.setOnClickListener{
            binding.progressBar.visibility = View.VISIBLE

            viewModel.getAllProductsFromServer()

            viewModel.productResponseResource.observe(this, Observer {
                when(it){
                    is Resource.Success -> {
                        hideProgressBar()
                        setupRecyclerView(it.data)
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        Utils.showToast(it.message.toString())
                    }
                    is Resource.Loading -> showProgressBar()
                }
            })


        }

        binding.buttonLoadSavedProducts.setOnClickListener{
            binding.progressBar.visibility = View.VISIBLE

            viewModel.getSavedProductFromDb()?.observe(this) {
                hideProgressBar()
                setupRecyclerView(it, true)
            }
        }
    }

    fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
    }

    private fun setupRecyclerView(products: List<Product>?,isSavedProduct : Boolean = false) {

        adapter = RecyclerViewAdapterForProducts(
            products?.toMutableList() ?: mutableListOf(),
            isSavedProduct,
            viewModel
        )

        // Setting the Adapter with the recyclerview
        binding.recyclerView.adapter = adapter
    }

}


