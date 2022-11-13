package com.androidready.demo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class FirstFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)

        println("Fragment : onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Fragment : onCreate")
       // addFragment(SecondFragment())

    }
    private fun addFragment(secondFragment: SecondFragment) {

        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.frameLayout,secondFragment)
        fragmentTransaction.commit()
        println("Fragment First : Fragment Added")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("Fragment : onCreateView")
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        println("Fragment : onActivityCreated")


    }

    override fun onStart() {
        super.onStart()
        println("Fragment : onStart")

    }

    override fun onResume() {
        super.onResume()
        println("Fragment : onResume")

    }

    override fun onPause() {
        super.onPause()
        println("Fragment : onPause")

    }

    override fun onStop() {
        super.onStop()
        println("Fragment : onStop")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("Fragment : onDestroyView")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("Fragment : onDestroy")

    }

    override fun onDetach() {
        super.onDetach()
        println("Fragment : onDetach")

    }

}