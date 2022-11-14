package com.androidready.demo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroupOverlay
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.FragmentOnAttachListener
import androidx.fragment.app.FragmentTransaction
import com.androidready.demo.R.*
import com.androidready.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),View.OnClickListener,UpdateActivity {

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

        fragmentManager.addFragmentOnAttachListener(
            FragmentOnAttachListener{ fragmentManager, fragment ->
                if(fragment == fragmentManager.findFragmentByTag("home")){
                     println("Home Fragment is attached")
                }
//                if(fragment == fragmentManager.findFragmentById(R.layout.fragment_home)){
//                    println("Home Fragment is attached")
//                }
        })

    }


    private fun addFragment(fragment : Fragment) {


        fragmentTransaction = fragmentManager.beginTransaction()
       // fragmentTransaction.add(id.frameLayout,fragment)

        //fragmentTransaction.replace(R.id.frameLayout,fragment)
        //fragmentTransaction.remove(R.id.frameLayout,fragment)

        if(fragment is HomeFragment){
            fragmentManager.popBackStack("home_fragment",POP_BACK_STACK_INCLUSIVE)
            fragmentTransaction.add(id.frameLayout,fragment,"home")
            fragmentTransaction.addToBackStack("home_fragment")
            fragment.setUpdateActivityObject(this)
        }else{
            fragmentTransaction.add(id.frameLayout,fragment)
            fragmentTransaction.addToBackStack(null)
        }

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

    @SuppressLint("ResourceType")
    override fun onBackPressed() {
        //fragmentManager.popBackStack()


        super.onBackPressed()
    }

    override fun onClick(view : View?) {

        if (view != null) {
            when(view.id){
                id.button_home -> addFragment(HomeFragment())
                id.button_first -> addFragment(FirstFragment())
                id.button_second -> addFragment(SecondFragment())
            }
        }
    }

    override fun updateActivityForDataFetch() {
        println("Activity is Updated for Data Fetch")
    }


}