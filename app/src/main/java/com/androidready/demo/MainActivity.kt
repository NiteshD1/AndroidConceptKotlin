package com.androidready.demo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.androidready.demo.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import timber.log.Timber

class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var thread: Thread
    private lateinit var thread1: Thread

    private lateinit var backgroundThread: BackgroundThread

    var isThreadRunning = false
    var isThreadRunning1 = false

    var threadCounter = 0
    var threadCounter1 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        println("Activity : onCreate")

        backgroundThread = BackgroundThread()
        backgroundThread.start()

        setupThread()
        setupThread1()

    }

    private fun setupThread() {

        val runnable:Runnable = Runnable {
            while (isThreadRunning) {
                threadCounter += 1

                runOnUiThread {
                    binding.textViewThread.text = "Thread Running : $threadCounter"
                }
                SystemClock.sleep(1000)
            }
        }
//        thread = Thread(Runnable {
//            while (isThreadRunning){
//                threadCounter += 1
//
//                runOnUiThread {
//                    binding.textViewThread.text = "Thread Running : $threadCounter"
//                }
//                Thread.sleep(1000)
//            }
//        })


        binding.buttonStartThread.setOnClickListener(View.OnClickListener {
            isThreadRunning = true
            //thread.start()
            backgroundThread.addTaskToMessageQueue(runnable)
        })

        binding.buttonStopThread.setOnClickListener(View.OnClickListener {
            isThreadRunning = false
            backgroundThread.removeTaskFromMessageQueue(runnable)
        })
    }

    private fun setupThread1() {

        val runnable:Runnable = Runnable {
            while (isThreadRunning1) {
                threadCounter1 += 1

                runOnUiThread {
                    binding.textViewThread1.text = "Thread 1 Running : $threadCounter1"
                }
                SystemClock.sleep(1000)
            }
        }

//        thread1 = Thread(Runnable {
//            while (isThreadRunning){
//                threadCounter1 += 1
//
//                runOnUiThread {
//                    binding.textViewThread1.text = "Thread 1 Running : $threadCounter1"
//                }
//                Thread.sleep(1000)
//            }
//        })

        binding.buttonStartThread1.setOnClickListener(View.OnClickListener {
            isThreadRunning1 = true
            //thread1.start()
            backgroundThread.addTaskToMessageQueue(runnable)

        })

        binding.buttonStopThread1.setOnClickListener(View.OnClickListener {
            isThreadRunning1 = false
            backgroundThread.removeTaskFromMessageQueue(runnable)

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


