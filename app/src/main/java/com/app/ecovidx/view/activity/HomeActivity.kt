package com.app.ecovidx.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.ecovidx.R
import com.app.ecovidx.databinding.ActivityHomeBinding
import com.app.ecovidx.utils.Constants

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.signOut.setOnClickListener {

            val sharedPref = this.getSharedPreferences("access_token", Context.MODE_PRIVATE)
                ?: return@setOnClickListener
            sharedPref.edit().clear().apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}