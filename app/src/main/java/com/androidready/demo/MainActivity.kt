package com.androidready.demo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroupOverlay
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.FragmentOnAttachListener
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import com.androidready.demo.R.*
import com.androidready.demo.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), View.OnClickListener,
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    val fragmentManager = supportFragmentManager
    private lateinit var fragmentTransaction: FragmentTransaction


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        println("Activity : onCreate")


        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, string.nav_open, string.nav_close)

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navigationView.setNavigationItemSelectedListener(this)

        setupBottomNavigation()

    }

    private fun setupBottomNavigation() {
        val homeFragment=HomeFragment()
        val settingFragment=SettingFragment()
        val profileFragment=ProfileFragment()

        setCurrentFragment(homeFragment)

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                id.item_home->setCurrentFragment(homeFragment)
                id.item_setting->setCurrentFragment(settingFragment)
                id.item_profile->setCurrentFragment(profileFragment)

            }
            true
        }

//        binding.bottomNavigation.setOnNavigationItemSelectedListener {
//            when(it.itemId){
//                id.item_home-> {
//                    println("home selected")
//                    Navigation.findNavController(this, R.id.fragmentContainerView)
//                        .navigate(R.id.action_global_homeFragment)
//                }
//                id.item_setting-> {
//                    println("setting selected")
//                    Navigation.findNavController(this, R.id.fragmentContainerView)
//                        .navigate(R.id.action_global_settingFragment)
//                }
//                id.item_profile-> {
//                    println("profile selected")
//                    Navigation.findNavController(this, R.id.fragmentContainerView)
//                        .navigate(R.id.action_global_profileFragment)
//                }
//            }
//            true
//        }
    }

    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView,fragment)
            commit()
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_setting -> {
                println("setting selected")
                Navigation.findNavController(this, R.id.fragmentContainerView)
                    .navigate(R.id.action_global_settingFragment)
            }
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


}