package com.androidready.demo.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.androidready.demo.Utils
import com.androidready.demo.databinding.ActivityMainBinding
import com.androidready.demo.model.api.RetrofitInstance
import com.androidready.demo.model.db.room.ProductDatabase
import com.androidready.demo.model.models.Product
import com.androidready.demo.view.adapter.RecyclerViewAdapterForProducts
import kotlinx.coroutines.*
import java.io.*

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private var db: ProductDatabase? = null
    var productList : List<Product>? = listOf<Product>()
    var savedProductList = mutableListOf<Product>()
    private lateinit var adapter : RecyclerViewAdapterForProducts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        println("Activity : onCreate")
        db = ProductDatabase.getInstance()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

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

    suspend fun displaySavedProductFromDb() {
        var listOfProducts = db?.getProductDao()?.getAllProducts() ?: mutableListOf()

        listOfProducts.let {
             withContext(Dispatchers.Main){
                 binding.progressBar.visibility = View.GONE
                 savedProductList = listOfProducts
                 setupRecyclerView(savedProductList,true)
             }
         }
    }

    suspend fun displayAllProductFromNetwork() {
        var responseProductList = RetrofitInstance.api.getProductList()

        if(responseProductList.isSuccessful){
            responseProductList.body().let { listOfProducts ->

                println("Product Data : ${listOfProducts.toString()}")
                listOfProducts.let {
                    withContext(Dispatchers.Main){
                        binding.progressBar.visibility = View.GONE
                        productList = listOfProducts
                        setupRecyclerView(productList)
                    }
                }
            }
        }else{
            println("Product List could not be fetched" + responseProductList.errorBody().toString())
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

    private suspend fun showAllProducts() {
         var products = db?.getProductDao()?.getAllProducts() ?: mutableListOf()

//         products.let {
//             withContext(Dispatchers.Main){
//                            val arrayAdapter = ArrayAdapter(this@MainActivity,android.R.layout.simple_list_item_1,it)
//                            binding.listView.adapter = arrayAdapter
//                        }
//         }
    }

    private suspend fun addProductToDb(product: Product) {
        db?.getProductDao()?.upsert(product)
           withContext(Dispatchers.Main){
               Utils.showToast("Product Added")
           }
    }


//    private fun retrofitDemo() {
//        GlobalScope.launch(Dispatchers.IO) {
//            var responseProductList = RetrofitInstance.api.getProductList()
//            //delay(2000)
//
//            if(responseProductList.isSuccessful){
//                responseProductList.body().let { productList ->
//                    var mutableListOfProduct : MutableList<String> = mutableListOf()
//                    productList?.forEach {
//                        println("Product Data : ${it.toString()}")
//                        val productInfo = "Product Id : ${it.id} \nProduct Title : ${it.title} \nProduct Price : ${it.price} \n"
//                        mutableListOfProduct.add(productInfo)
//                    }
//                    mutableListOfProduct.let {
//                        withContext(Dispatchers.Main){
//                            val arrayAdapter = ArrayAdapter(this@MainActivity,android.R.layout.simple_list_item_1,mutableListOfProduct)
//                            binding.listView.adapter = arrayAdapter
//                        }
//                    }
//                }
//            }else{
//                responseProductList.errorBody().let {
//                    println("Product List could not be fetched" + it.toString())
//                }
//            }
//        }
//    }




    @SuppressLint("ResourceType")
    override fun onBackPressed() {
        //fragmentManager.popBackStack()


        super.onBackPressed()
    }



}


