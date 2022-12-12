package com.androidready.demo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SecondFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)

        println("Second Fragment : onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Second Fragment : onCreate")
        // addFragment(SecondFragment())

    }
    private fun addFragment(secondFragment: SecondFragment) {

        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.frameLayout,secondFragment)
        fragmentTransaction.commit()
        println("Second Fragment First : Fragment Added")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("Second Fragment : onCreateView")
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        println("Second Fragment : onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        println("Second Fragment : onStart")

    }

    override fun onResume() {
        super.onResume()
        println("Second Fragment : onResume")

    }

    override fun onPause() {
        super.onPause()
        println("Second Fragment : onPause")

    }

    override fun onStop() {
        super.onStop()
        println("Second Fragment : onStop")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("Second Fragment : onDestroyView")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("Second Fragment : onDestroy")

    }

    override fun onDetach() {
        super.onDetach()
        println("Second Fragment : onDetach")

    }

}