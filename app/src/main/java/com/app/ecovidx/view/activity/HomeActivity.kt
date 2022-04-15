package com.app.ecovidx.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.app.ecovidx.R
import com.app.ecovidx.databinding.ActivityHomeBinding
import com.app.ecovidx.repository.HomeRepository
import com.app.ecovidx.viewmodel.HomeViewModel
import com.app.ecovidx.viewmodel.providers.HomeViewModelProviderFactory



class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val repository = HomeRepository()
        val viewModelProviderFactory = HomeViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[HomeViewModel::class.java]

//        val backView = findViewById<View>(R.id.home_view)
//        backView.setOnClickListener {
//
//            val sharedPref = this.getSharedPreferences("access_token", Context.MODE_PRIVATE)
//                ?: return@setOnClickListener
//            sharedPref.edit().clear().apply()
//
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.search_bar, menu)
//
//        val search = menu.findItem(R.id.search)
//        val searchView = search.actionView as SearchView
//        searchView.queryHint = "Search"
//        val searchPlate = searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text)
//        searchPlate.setBackgroundResource(R.drawable.search_background)
//        searchView.setIconifiedByDefault(false)
//        searchView.maxWidth
//
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//            override fun onQueryTextChange(newText: String?): Boolean {
//
//                return true
//            }
//        })
//        return super.onCreateOptionsMenu(menu)
//    }
}