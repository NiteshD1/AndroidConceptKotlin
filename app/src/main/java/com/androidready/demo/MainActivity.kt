package com.androidready.demo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import androidx.appcompat.app.AppCompatActivity
import com.androidready.demo.api.RetrofitInstance
import com.androidready.demo.databinding.ActivityMainBinding
import com.androidready.demo.models.Product
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        println("Activity : onCreate")

        binding.buttonFetchData.setOnClickListener(View.OnClickListener {
            retrofitDemo()
        })
    }

    private fun retrofitDemo() {
        GlobalScope.launch(Dispatchers.IO) {
            var responseProductList = RetrofitInstance.api.getProductList()
            //delay(2000)

            if(responseProductList.isSuccessful){
                responseProductList.body().let { productList ->
                    var mutableListOfProduct : MutableList<String> = mutableListOf()
                    productList?.forEach {
                        println("Product Data : ${it.toString()}")
                        val productInfo = "Product Id : ${it.id} \nProduct Title : ${it.title} \nProduct Price : ${it.price} \n"
                        mutableListOfProduct.add(productInfo)
                    }
                    withContext(Dispatchers.Main){
                        val arrayAdapter = ArrayAdapter(this@MainActivity,android.R.layout.simple_list_item_1,mutableListOfProduct)
                        binding.listView.adapter = arrayAdapter
                    }
                }
            }else{
                responseProductList.errorBody().let {
                    println("Product List could not be fetched" + it.toString())
                }
            }
        }
    }



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


