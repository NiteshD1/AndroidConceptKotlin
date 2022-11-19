package com.androidready.demo

import android.widget.Toast
import timber.log.Timber

class Utils {
      companion object{
          fun showToast(message : String){
              Toast.makeText(MainApplication.appContext,message,Toast.LENGTH_LONG).show()
          }

          fun showGlobalLog(message: String){
              Timber.tag("AppLog").d(message)
          }
      }
}

