package com.app.ecovidx.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.app.ecovidx.R
import com.app.ecovidx.db.CartDatabase
import com.app.ecovidx.repository.CartRepository
import com.app.ecovidx.repository.HomeRepository
import com.app.ecovidx.view.fragment.home.HomeFragmentDirections
import com.app.ecovidx.view.fragment.home.ProductDetailFragmentDirections
import com.app.ecovidx.view.fragment.home.ProductsByCategoryFragmentDirections
import com.app.ecovidx.viewmodel.CartViewModel
import com.app.ecovidx.viewmodel.HomeViewModel
import com.app.ecovidx.viewmodel.providers.CartViewModelFactory
import com.app.ecovidx.viewmodel.providers.HomeViewModelProviderFactory
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {

    lateinit var viewModel: HomeViewModel
    lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val repository = HomeRepository()
        val viewModelProviderFactory = HomeViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[HomeViewModel::class.java]

        val db = CartDatabase(this)
        val cartRepository = CartRepository(db)
        val cartViewModelProviderFactory = CartViewModelFactory(cartRepository)
        cartViewModel = ViewModelProvider(this, cartViewModelProviderFactory)[CartViewModel::class.java]

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.homeNavHostFragment) as NavHostFragment
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navHostFragment.navController)
        bottomNavigationView.menu.getItem(1).isEnabled = false

        val fab = findViewById<View>(R.id.fab)
        fab.setOnClickListener {
            when (navHostFragment.navController.currentDestination?.label) {
                "HomeFragment" -> findNavController(this, R.id.homeNavHostFragment)
                    .navigate(HomeFragmentDirections.actionHomeFragmentToAllProductsFragment())
                "ProductsByCategoryFragment" -> findNavController(this, R.id.homeNavHostFragment)
                    .navigate(ProductsByCategoryFragmentDirections.actionProductsByCategoryFragmentToAllProductsFragment())
                "ProductDetailFragment" -> findNavController(this, R.id.homeNavHostFragment)
                    .navigate(ProductDetailFragmentDirections.actionProductDetailFragmentToAllProductsFragment())
            }
        }

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