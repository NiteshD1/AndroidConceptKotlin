package com.androidready.demo


import android.os.Handler
import android.os.Looper


class BackgroundThread : Thread() {

    private lateinit var handler: Handler
    override fun run() {
        super.run()

        Looper.prepare()
        handler = Looper.myLooper()?.let { Handler(it) }!!

        //handler = Handler(Looper.getMainLooper())
        Looper.loop()
    }

    fun addTaskToMessageQueue(runnable: Runnable){
        handler.post(runnable)
    }

    fun removeTaskFromMessageQueue(runnable: Runnable){
        handler.removeCallbacks(runnable)
    }
}