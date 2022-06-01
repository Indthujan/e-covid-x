package com.app.ecovidx.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.app.ecovidx.R
import com.app.ecovidx.viewmodel.AuthViewModel
import com.app.ecovidx.repository.AuthRepository
import com.app.ecovidx.viewmodel.providers.AuthViewModelProviderFactory
import androidx.navigation.fragment.NavHostFragment




class MainActivity : AppCompatActivity() {

    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //AuthViewModel
        val repository = AuthRepository()
        val viewModelProviderFactory = AuthViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[AuthViewModel::class.java]
    }
}
