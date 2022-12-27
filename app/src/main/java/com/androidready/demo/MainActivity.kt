package com.androidready.demo

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.androidready.demo.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MainActivity : AppCompatActivity(), View.OnClickListener{

    private val TAG = "PermissionDemo"
    private val REQUEST_CODE = 101


    private lateinit var builder: Notification.Builder
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var notificationManager: NotificationManager
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        println("Activity : onCreate")

        binding.buttonRequestPermission.setOnClickListener(View.OnClickListener {
            //requestPermissions()
            checkPermission()
        })

        binding.buttonSendNotification.setOnClickListener(View.OnClickListener {
            addNotification()
        })

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermissions() {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.READ_CONTACTS)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        Toast.makeText(this@MainActivity, "All the permissions are granted..", Toast.LENGTH_SHORT).show()
                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied) {
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(list: List<PermissionRequest>, permissionToken: PermissionToken) {
                    permissionToken.continuePermissionRequest()
                }
            }).withErrorListener {
                Toast.makeText(applicationContext, "Error occurred! ", Toast.LENGTH_SHORT).show()
            }
            .onSameThread().check()
    }

    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this@MainActivity)

        builder.setTitle("Need Permissions")

        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS") { dialog, which ->
            dialog.cancel()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivityForResult(intent, 101)
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun addNotification() {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationChannel =
                NotificationChannel("123", "this is test Notification", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
            builder =
                Notification.Builder(this, "123").setContentTitle("Notification title")
                    .setContentText("Test Notification")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentIntent(pendingIntent)
        }
        notificationManager.notify(12345, builder.build())
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Utils.showToast("Permission has been denied by user")

                } else {
                    Utils.showToast("Permission has been granted by user")
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkPermission() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                showDialogForPermissionExplaination()
            } else {
                if(Utils.isPermissionAskedFirstTime){
                    makeRequest()
                    Utils.isPermissionAskedFirstTime = false
                }else{
                    showSettingsDialog()
                }
            }
        }else{
            Utils.showToast("Permission already given")
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showDialogForPermissionExplaination() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Notification permission is required for this app to send notifications.")
            .setTitle("Permission required")

        builder.setPositiveButton(
            "OK"
        ) { dialog, id ->
            Log.i(TAG, "Clicked")
            makeRequest()
        }

        val dialog = builder.create()
        dialog.show()
    }




    override fun onClick(view: View?) {


    }


}