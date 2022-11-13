package com.androidready.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroupOverlay
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.androidready.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    val fragmentManager = supportFragmentManager
    private lateinit var fragmentTransaction: FragmentTransaction


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        println("Activity : onCreate")

        binding.buttonHome.setOnClickListener(this)
        binding.buttonFirst.setOnClickListener(this)
        binding.buttonSecond.setOnClickListener(this)

    }

    private fun addFragment(fragment : Fragment) {

        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frameLayout,fragment)
        //fragmentTransaction.replace(R.id.frameLayout,fragment)
        //fragmentTransaction.remove(R.id.frameLayout,fragment)

        //fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()

        println("Activity : Fragment Added")

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

    override fun onBackPressed() {
        //fragmentManager.popBackStack()
        super.onBackPressed()
    }

    override fun onClick(view : View?) {

        if (view != null) {
            when(view.id){
                R.id.button_home -> addFragment(HomeFragment())
                R.id.button_first -> addFragment(FirstFragment())
                R.id.button_second -> addFragment(SecondFragment())
            }
        }
    }
}