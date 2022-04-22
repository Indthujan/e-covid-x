package com.app.ecovidx.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.ecovidx.db.entities.Cart

@Dao
interface CartDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: Cart)

    @Delete
    suspend fun delete(item: Cart)

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): LiveData<List<Cart>>
}