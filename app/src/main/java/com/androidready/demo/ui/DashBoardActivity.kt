package com.androidready.demo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidready.demo.MainApplication
import com.androidready.demo.R
import com.androidready.demo.databinding.ActivityDashBoardBinding
import com.androidready.demo.databinding.ActivityMainBinding

class DashBoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var email = MainApplication.prefs?.email

        binding.textView.text = "DashBoard Activity\n$email"

    }
}