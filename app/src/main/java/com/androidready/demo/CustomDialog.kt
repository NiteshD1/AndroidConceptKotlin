package com.androidready.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment


class CustomDialog : DialogFragment() {

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
        return inflater.inflate(R.layout.layout_custom_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupView(view: View) {
        val textViewHeading : TextView = view.findViewById(R.id.textViewHeading)
        val textViewDescription : TextView = view.findViewById(R.id.textViewDescription)

        textViewHeading.text = arguments?.getString(KEY_TITLE)
        textViewDescription.text = arguments?.getString(KEY_DESCRIPTION)
    }

    private fun setupClickListeners(view: View) {
        val buttonYes : Button = view.findViewById(R.id.buttonYes)
        val buttonCancel : Button = view.findViewById(R.id.buttonCancel)


        buttonYes.setOnClickListener {

            Utils.showToast("Transaction is processing...")
            dismiss()
        }
        buttonCancel.setOnClickListener {
            Utils.showToast("Transaction failed!")

            dismiss()
        }
    }

}