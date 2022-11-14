package com.androidready.demo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class HomeFragment : Fragment() {

    private lateinit var updateActivity: UpdateActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        println("Fragment : onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Fragment : onCreate")
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
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val fetchButton: Button = view.findViewById(R.id.button_fetch)

        fetchButton.setOnClickListener(View.OnClickListener {
            println("Data Fetched Button Clicked")
            updateActivity.updateActivityForDataFetch()
        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        println("Fragment : onActivityCreated")


    }

    fun setUpdateActivityObject(updateActivity: UpdateActivity){
        this.updateActivity = updateActivity
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