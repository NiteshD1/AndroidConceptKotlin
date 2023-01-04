package com.androidready.demo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.androidready.demo.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var job: Job


    var isThreadRunning = false
    var isThreadRunning1 = false

    var counter = 0
    var counter1 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        println("Activity : onCreate")

        //coroutineDemo()
        //setupCoroutine()
        setupCoroutine1()


        binding.buttonStartActivity.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,TestActivity::class.java))
            finish()
        })

        dispatcherDemo()

        //asyncDemo()
        //runBlockingDemo()
    }

    private fun runBlockingDemo() {
        runBlocking {
            delay(5000)
            println("runBlocking Called")
        }
    }

    private fun asyncDemo() {
        GlobalScope.launch(Dispatchers.Main) {
            val serverData = async { recieveDataFromServer() }
            println(serverData.await())
        }
    }

    suspend fun recieveDataFromServer(): String {
          delay(1000)
          return "Data Received From Server"
    }

    private fun dispatcherDemo() {
//        GlobalScope.launch(Dispatchers.Main) {
//            Thread.sleep(5000)
//            fetchDataFromServer()
//        }

//        GlobalScope.launch(Dispatchers.IO) {
//            Thread.sleep(5000)
//
//            fetchDataFromServer()
//        }

//        GlobalScope.launch(Dispatchers.Default) {
//            fetchDataFromServer()
//        }
    }

    private fun coroutineDemo() {
        GlobalScope.launch {
            fetchDataFromServer()

        }

//        lifecycleScope.launch {
//            fetchDataFromServer()
//        }

    }

    suspend fun fetchDataFromServer(){
        println("fetching data from server...")

        // to show that global scope still working after changing activity
        while (true){
            counter += 1
            println("fetching data from server... $counter")

            // what happens if I Use main activity objet
            binding.textViewThread.text = "Coroutine Running : $counter"

            delay(1000)
        }
    }

    suspend fun startCoroutine(){
        while (isThreadRunning) {
            counter += 1
            binding.textViewThread.text = "Coroutine Running : $counter"
            delay(1000)
        }
    }

    suspend fun startCoroutine1(){
        while (true) {
            counter1 += 1
            binding.textViewThread1.text = "Coroutine 1 Running : $counter1"
            delay(1000)
        }
    }

    private fun setupCoroutine() {



//        val runnable:Runnable = Runnable {
//            while (isThreadRunning) {
//                threadCounter += 1
//
//                runOnUiThread {
//                    binding.textViewThread.text = "Thread Running : $threadCounter"
//                }
//                SystemClock.sleep(1000)
//            }
//        }

        binding.buttonStartCoroutine.setOnClickListener(View.OnClickListener {
            GlobalScope.launch {
                isThreadRunning = true
                startCoroutine()
                //startCoroutine1() calling it for showing sequential call in coroutine
                val job = launch { startCoroutine() }
            }
        })

        binding.buttonStopCoroutine.setOnClickListener(View.OnClickListener {
            isThreadRunning = false
        })
    }

    private fun setupCoroutine1() {

        binding.buttonStartCoroutine1.setOnClickListener(View.OnClickListener {
            GlobalScope.launch {
                job = launch { startCoroutine1() }
            }
        })

        binding.buttonStopCoroutine1.setOnClickListener(View.OnClickListener {
              job.cancel()
        })

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


