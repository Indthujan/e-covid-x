package com.app.ecovidx.view.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.app.ecovidx.utils.Constants
import com.app.ecovidx.view.activity.HomeActivity
import com.app.ecovidx.view.activity.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            validateAccessToken()
            delay(2000)
        }
    }

    private fun validateAccessToken() {
        val sharedPref = this.getSharedPreferences("access_token", Context.MODE_PRIVATE) ?: return
        val token = sharedPref.getString(Constants.ACCESS_TOKEN, Constants.NO_TOKEN)

        if (token == Constants.NO_TOKEN) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}