package com.androidready.demo.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.androidready.demo.MainApplication
import com.androidready.demo.Utils
import com.androidready.demo.api.RetrofitInstance
import com.androidready.demo.databinding.ActivityMainBinding
import com.androidready.demo.db.room.ProductDatabase
import com.androidready.demo.models.Product
import com.androidready.demo.models.Rating
import kotlinx.coroutines.*
import java.io.*

class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var myExternalFile: File
    private lateinit var binding: ActivityMainBinding
    val filename = "myfile"
    val externalFileName = "AndroidReadyDemoFile"
    private val externalStorageFilePath = "AndroidReadyFolder"
    private lateinit var db: ProductDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        println("Activity : onCreate")
        db = ProductDatabase(this)


        binding.buttonAddProduct.setOnClickListener(View.OnClickListener {
            GlobalScope.launch {
                val id = binding.editTextId.text.toString().toInt()
                val name = binding.editTextName.text.toString()
                val price = binding.editTextPrice.text.toString().toInt()
                val product = Product(id,name,price)
                addProductToDb(product)
            }
        })

        binding.buttonShowProducts.setOnClickListener(View.OnClickListener {
            GlobalScope.launch{
                showAllProducts()
            }
        })

        binding.buttonDeleteProducts.setOnClickListener(View.OnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                if(binding.editTextId.text.toString().equals("")){
                    db.getProductDao().deleteAll()
                }else{
                    val id = binding.editTextId.text.toString().toInt()
                    db.getProductDao().deleteById(id)
                }
                withContext(Dispatchers.Main){
                    Utils.showToast("Deleted")
                }
            }

        })

    }

    private suspend fun showAllProducts() {
         var products = db.getProductDao().getAllProducts()

         products.let {
             withContext(Dispatchers.Main){
                            val arrayAdapter = ArrayAdapter(this@MainActivity,android.R.layout.simple_list_item_1,it)
                            binding.listView.adapter = arrayAdapter
                        }
         }
    }

    private suspend fun addProductToDb(product: Product) {
           db.getProductDao().upsert(product)
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



    override fun onStart() {
        super.onStart()
        println("Activity : onStart")

    }

    override fun onResume() {
        super.onResume()
        println("Activity : onResume")

    }

    override fun onPause() {
        super.onPause()
        println("Activity : onPause")

    }

    override fun onStop() {
        super.onStop()
        println("Activity : onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("Activity : onDestroy")

    }

    @SuppressLint("ResourceType")
    override fun onBackPressed() {
        //fragmentManager.popBackStack()


        super.onBackPressed()
    }

    override fun onClick(view: View?) {


    }


}


