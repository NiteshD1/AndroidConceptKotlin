package com.androidready.demo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.androidready.demo.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        println("Activity : onCreate")

        setupCustomDialog()


    }

    private fun setupCustomDialog() {
        binding.buttonCustomDialog.setOnClickListener {
            CustomDialog.newInstance(getString(R.string.dialog_heading), getString(R.string.dialog_description))
                .show(supportFragmentManager, CustomDialog.TAG)
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