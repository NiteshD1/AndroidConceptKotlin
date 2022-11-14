package com.androidready.demo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.androidready.demo.databinding.FragmentFirstBinding
import com.androidready.demo.databinding.FragmentHomeBinding

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    val args: FirstFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        println("First Fragment : onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("First Fragment : onCreate")
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
        binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.buttonSetting.setOnClickListener(View.OnClickListener {
            it.findNavController().navigate(R.id.action_global_settingFragment)
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        println("First Fragment : onActivityCreated")

        binding.textViewForSafeArg.text = args.data

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