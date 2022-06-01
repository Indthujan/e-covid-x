package com.app.ecovidx.viewmodel

import androidx.lifecycle.ViewModel
import com.app.ecovidx.db.entities.Cart
import com.app.ecovidx.repository.CartRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: CartRepository
) : ViewModel() {

    fun upsert(item: Cart) = CoroutineScope(Dispatchers.Main).launch {
        repository.upsert(item)
    }

    fun delete(item: Cart) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }

    fun getAllShoppingItems() = repository.getAllCartItems()

    fun deleteAllCartItems() = CoroutineScope(Dispatchers.Main).launch {
        repository.deleteCartItems()
    }
}