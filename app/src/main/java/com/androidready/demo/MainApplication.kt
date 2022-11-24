package com.androidready.demo

import android.app.Application
import android.content.Context
import com.androidready.demo.db.Prefs
import timber.log.Timber

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MainApplication.appContext = applicationContext
        MainApplication.prefs = Prefs(applicationContext)

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree());
        }
    }



    companion object {

        lateinit  var appContext: Context

        var prefs: Prefs? = null


    }

}

