package com.androidready.demo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.androidready.demo.api.RetrofitInstance
import com.androidready.demo.databinding.ActivityMainBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.*
import java.net.URL


class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var myExternalFile: File
    private lateinit var binding: ActivityMainBinding
    val filename = "myfile"
    val externalFileName = "AndroidReadyDemoFile.txt"
    private val externalStorageFilePath = "AndroidReadyFolder"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        println("Activity : onCreate")

        //createFileInternalStorage()

        binding.buttonWriteInternal.setOnClickListener(View.OnClickListener {
            writeInPrivateFile()
        })

        binding.buttonReadInternal.setOnClickListener(View.OnClickListener {
            readFromPrivateFile()
        })

        binding.buttonRequestPermission.setOnClickListener(View.OnClickListener {
            requestPermissions()
        })

        binding.buttonWriteExternal.setOnClickListener(View.OnClickListener {
            writeInExternal()
        })

        binding.buttonReadExternal.setOnClickListener(View.OnClickListener {
            readFromExternalFile()
        })
        binding.buttonFetchData.setOnClickListener(View.OnClickListener {
            retrofitDemo()
        })

        GlobalScope.launch(Dispatchers.IO) {
            saveImageToPublicStorage()
        }
    }

    private suspend fun saveImageToPublicStorage() {
        try {
            val url = URL("https://picsum.photos/200/300")
            val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            saveMediaToStorage(image)
        } catch (e: IOException) {
            println(e)
        }
    }

    private fun readFromExternalFile() {
        myExternalFile = File(getExternalFilesDir(externalStorageFilePath), externalFileName)

        val stringBuffer = StringBuffer()
        try {
            //Attaching BufferedReader to the FileInputStream by the help of InputStreamReader
            val inputReader = InputStreamReader(FileInputStream(myExternalFile))
            inputReader.forEachLine { stringBuffer.append(it) }

            stringBuffer.let {
                binding.textView.text = it.toString()
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    suspend fun saveMediaToStorage(bitmap: Bitmap) {
        //Generating a file name
        val filename = "${System.currentTimeMillis()}.jpg"

        //Output stream
        var fos: OutputStream? = null


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
        {
            //These for devices running on android < Q
            //So I don't think an explanation is needed here
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            //Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            withContext(Dispatchers.Main){
                Utils.showToast("Saved to Photos")
            }
        }
    }

    private fun writeInExternal() {
        val outputStream: FileOutputStream
        var fileContents = "Dummy Content"

        binding.editText.let {
            fileContents = it.text.toString()
        }

        myExternalFile = File(getExternalFilesDir(externalStorageFilePath), externalFileName)
        try {
            outputStream = FileOutputStream(myExternalFile)
            outputStream.write(fileContents.toByteArray())
            outputStream.close()
            Utils.showToast("Write Operation Done!")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val isExternalStorageReadOnly: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)
    }
    private val isExternalStorageAvailable: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED.equals(extStorageState)
    }

    private fun requestPermissions() {
        // below line is use to request permission in the current activity.
        // this method is use to handle error in runtime permissions
        Dexter.withActivity(this)
            // below line is use to request the number of permissions which are required in our app.
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            // after adding permissions we are calling an with listener method.
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                    // this method is called when all permissions are granted
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        // do you work now
                        Toast.makeText(this@MainActivity, "All the permissions are granted..", Toast.LENGTH_SHORT).show()
                    }
                    // check for permanent denial of any permission
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied) {
                        // permission is denied permanently, we will show user a dialog message.
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(list: List<PermissionRequest>, permissionToken: PermissionToken) {
                    // this method is called when user grants some permission and denies some of them.
                    permissionToken.continuePermissionRequest()
                }
            }).withErrorListener {
                // we are displaying a toast message for error message.
                Toast.makeText(applicationContext, "Error occurred! ", Toast.LENGTH_SHORT).show()
            }
            // below line is use to run the permissions on same thread and to check the permissions
            .onSameThread().check()
    }
    // below is the shoe setting dialog method
    // which is use to display a dialogue message.
    private fun showSettingsDialog() {
        // we are displaying an alert dialog for permissions
        val builder = AlertDialog.Builder(this@MainActivity)

        // below line is the title for our alert dialog.
        builder.setTitle("Need Permissions")

        // below line is our message for our dialog
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS") { dialog, which ->
            // this method is called on click on positive button and on clicking shit button
            // we are redirecting our user from our app to the settings page of our app.
            dialog.cancel()
            // below is the intent from which we are redirecting our user.
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivityForResult(intent, 101)
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            // this method is called when user click on negative button.
            dialog.cancel()
        }
        // below line is used to display our dialog
        builder.show()
    }
    private fun readFromPrivateFile() {
        val stringBuffer = StringBuffer()
        try {
            //Attaching BufferedReader to the FileInputStream by the help of InputStreamReader
            val inputReader = BufferedReader(InputStreamReader(openFileInput(filename)))
            inputReader.forEachLine { stringBuffer.append(it) }

            stringBuffer.let {
                binding.textView.text = it.toString()
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    private fun writeInPrivateFile() {

        val outputStream: FileOutputStream
        var fileContents = "Dummy Content"

        binding.editText.let {
            fileContents = it.text.toString()
        }
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE)
            outputStream.write(fileContents.toByteArray())
            outputStream.close()
            Utils.showToast("Write Operation Done!")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createFileInternalStorage() {
        val file = File(this.filesDir, "demoFile")
        println("File Path is : ${file.absolutePath}")
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


