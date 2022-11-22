package com.androidready.demo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.androidready.demo.api.RetrofitInstance
import com.androidready.demo.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        println("Activity : onCreate")

        retrofitDemo()

    }

    private fun retrofitDemo() {
        GlobalScope.launch {
            var productList = RetrofitInstance.api.getProductList()
            delay(2000)
            println("Product List : " + productList.toString())
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


