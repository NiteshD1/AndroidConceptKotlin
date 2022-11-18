package com.androidready.demo

import android.widget.Toast

class Utils {
      companion object{
          fun showToast(message : String){
              Toast.makeText(MainApplication.appContext,message,Toast.LENGTH_LONG).show()
          }
      }
}