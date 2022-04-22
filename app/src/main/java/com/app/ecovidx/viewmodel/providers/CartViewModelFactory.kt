package com.app.ecovidx.viewmodel.providers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.ecovidx.repository.CartRepository
import com.app.ecovidx.viewmodel.CartViewModel

@Suppress("UNCHECKED_CAST")
class CartViewModelFactory(
    private val repository: CartRepository
): ViewModelProvider.Factory{


    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return CartViewModel(repository) as T
    }
}