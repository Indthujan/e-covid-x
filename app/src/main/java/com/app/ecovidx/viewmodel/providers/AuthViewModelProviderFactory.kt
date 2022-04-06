package com.app.ecovidx.viewmodel.providers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.ecovidx.viewmodel.AuthViewModel
import com.app.ecovidx.repository.AuthRepository

class AuthViewModelProviderFactory(
    val authRepository: AuthRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return AuthViewModel(authRepository) as T
    }
}