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
import kotlinx.coroutines.*
import java.io.*

class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var myExternalFile: File
    private lateinit var binding: ActivityMainBinding
    val filename = "myfile"
    val externalFileName = "AndroidReadyDemoFile"
    private val externalStorageFilePath = "AndroidReadyFolder"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        println("Activity : onCreate")


//        MainApplication.prefs?.email = "my email"
//        println(MainApplication.prefs?.email)

        binding.buttonSignUp.setOnClickListener(View.OnClickListener {
            signUp()
        })

        var email = MainApplication.prefs?.email
        if(email != null){
            startDashBoardActivity()
        }


    }

    private fun signUp() {
        if(binding.editTextEmail.text != null){
            MainApplication.prefs?.email = binding.editTextEmail.text.toString()
            startDashBoardActivity()
        }else{
            Utils.showToast("Enter EMail First!")
        }
    }

    private fun startDashBoardActivity() {
        startActivity(Intent(this,DashBoardActivity::class.java))
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
                    mutableListOfProduct.let {
                        withContext(Dispatchers.Main){
                            val arrayAdapter = ArrayAdapter(this@MainActivity,android.R.layout.simple_list_item_1,mutableListOfProduct)
                            binding.listView.adapter = arrayAdapter
                        }
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


