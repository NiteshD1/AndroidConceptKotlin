package com.androidready.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.androidready.demo.databinding.ActivityMainBinding
import com.androidready.demo.databinding.LayoutCustomDialogBinding


class CustomDialog : DialogFragment() {
    private lateinit var binding: LayoutCustomDialogBinding

    companion object {

        const val TAG = "CustomDialog"

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_DESCRIPTION = "KEY_DESCRIPTION"

        fun newInstance(title: String, subTitle: String): CustomDialog {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_DESCRIPTION, subTitle)
            val fragment = CustomDialog()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = LayoutCustomDialogBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setupView()
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()

    }

    private fun setupView() {
        binding.textViewHeading.text = arguments?.getString(KEY_TITLE)
        binding.textViewDescription.text = arguments?.getString(KEY_DESCRIPTION)
    }

    private fun setupClickListeners(view: View) {

        binding.buttonYes.setOnClickListener {
            Utils.showToast("Transaction is processing...")
            dismiss()
        }

        binding.buttonCancel.setOnClickListener {
            Utils.showToast("Transaction failed!")
            dismiss()
        }
    }

}