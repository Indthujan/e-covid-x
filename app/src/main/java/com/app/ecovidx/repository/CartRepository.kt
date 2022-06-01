package com.app.ecovidx.repository

import com.app.ecovidx.db.CartDatabase
import com.app.ecovidx.db.entities.Cart

class CartRepository (
    private val db: CartDatabase
) {
    suspend fun upsert(item: Cart) = db.getCartDAO().upsert(item)

    suspend fun delete(item: Cart) = db.getCartDAO().delete(item)

    fun getAllCartItems() = db.getCartDAO().getAllCartItems()

    suspend fun deleteCartItems() = db.getCartDAO().deleteCartItems()
}