package com.androidready.demo.worker

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

import androidx.work.Worker
import androidx.work.WorkerParameters
import com.androidready.demo.MainActivity
import com.androidready.demo.R

class BackgroundTaskWorker(val context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationManager: NotificationManager
    lateinit var builder: Notification.Builder
    private val channelId = "12345"
    private val description = "Test Notification"

    override fun doWork(): Result {

        startBackgroundTask()
        return Result.success()
    }

    private fun startBackgroundTask() {
        println("Work Manager Started for Background Task")
        addNotification()
    }


    private fun addNotification() {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationChannel =
                NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(context, channelId).setContentTitle("Work Manager Started")
                .setContentText("Test Notification")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(12345, builder.build())
    }
}