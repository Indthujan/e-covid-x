package com.app.ecovidx.viewmodel.providers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.ecovidx.repository.HomeRepository
import com.app.ecovidx.viewmodel.HomeViewModel

class HomeViewModelProviderFactory(
    val homeRepository: HomeRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return HomeViewModel(homeRepository) as T
    }
}