package com.androidready.demo.model.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androidready.demo.MainApplication
import com.androidready.demo.model.models.Product

@Database(
    entities = [Product::class],
    version = 3
)


abstract class ProductDatabase : RoomDatabase() {

    abstract fun getProductDao(): ProductDao



    companion object {

        private var instance: ProductDatabase? = null

        fun getInstance() : ProductDatabase? {
            return instance ?: createDatabase()
        }

        private fun createDatabase(): ProductDatabase? {
            instance = Room.databaseBuilder(
                MainApplication.appContext,
                ProductDatabase::class.java,
                "product_db.db"
            ).build()
            return instance
        }
    }
}