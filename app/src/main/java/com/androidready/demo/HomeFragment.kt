package com.androidready.demo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.androidready.demo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)

        println("Fragment : onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Fragment : onCreate")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("Fragment : onCreateView")
        binding = FragmentHomeBinding.inflate(inflater, container, false)



        return binding.root
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