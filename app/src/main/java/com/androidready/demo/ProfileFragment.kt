package com.androidready.demo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.androidready.demo.databinding.FragmentHomeBinding
import com.androidready.demo.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        println("First Fragment : onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("First Fragment : onCreate")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("Fragment : onCreateView")
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        println("First Fragment : onActivityCreated")



    }

    override fun onStart() {
        super.onStart()
        println("First Fragment : onStart")

    }

    override fun onResume() {
        super.onResume()
        println("First Fragment : onResume")

    }

    override fun onPause() {
        super.onPause()
        println("First Fragment : onPause")

    }

    override fun onStop() {
        super.onStop()
        println("First Fragment : onStop")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("First Fragment : onDestroyView")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("First Fragment : onDestroy")

    }

    override fun onDetach() {
        super.onDetach()
        println("First Fragment : onDetach")

    }

}