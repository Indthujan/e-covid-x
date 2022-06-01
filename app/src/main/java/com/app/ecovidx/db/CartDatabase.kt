package com.app.ecovidx.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.ecovidx.db.entities.Cart

@Database(
    entities = [Cart::class],
    version = 5
)
abstract class CartDatabase : RoomDatabase() {

    abstract fun getCartDAO(): CartDAO

    companion object {
        @Volatile
        private var instance: CartDatabase? = null

        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDataBase(context).also { instance = it }
        }

        private fun createDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CartDatabase::class.java,
                "CartDB.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}