package com.androidready.demo

import android.app.Application
import android.content.Context
import com.androidready.demo.model.db.Prefs
import com.androidready.demo.model.db.room.ProductDatabase
import timber.log.Timber

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MainApplication.appContext = applicationContext
        MainApplication.prefs = Prefs(applicationContext)
        db = ProductDatabase.getInstance()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree());
        }
    }



    companion object {

        lateinit  var appContext: Context

        var prefs: Prefs? = null
        var db: ProductDatabase? = null

    }

}

