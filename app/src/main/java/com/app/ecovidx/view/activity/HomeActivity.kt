package com.app.ecovidx.view.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.ecovidx.R
import com.app.ecovidx.db.CartDatabase
import com.app.ecovidx.repository.CartRepository
import com.app.ecovidx.repository.HomeRepository
import com.app.ecovidx.utils.Constants
import com.app.ecovidx.utils.Resource
import com.app.ecovidx.view.fragment.home.*
import com.app.ecovidx.view.fragment.user.ProfileFragment
import com.app.ecovidx.viewmodel.CartViewModel
import com.app.ecovidx.viewmodel.HomeViewModel
import com.app.ecovidx.viewmodel.providers.CartViewModelFactory
import com.app.ecovidx.viewmodel.providers.HomeViewModelProviderFactory
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {

    lateinit var viewModel: HomeViewModel
    lateinit var cartViewModel: CartViewModel
    private var backPressedTime: Long = 0
    private val fm = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //HomeViewModel
        val repository = HomeRepository()
        val viewModelProviderFactory = HomeViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[HomeViewModel::class.java]

        //CarViewModel
        val db = CartDatabase(this)
        val cartRepository = CartRepository(db)
        val cartViewModelProviderFactory = CartViewModelFactory(cartRepository)
        cartViewModel =
            ViewModelProvider(this, cartViewModelProviderFactory)[CartViewModel::class.java]

        //LaunchFragment
        loadFragment(HomeFragment())

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.itemIconTintList = null
        bottomNavigationView.menu.getItem(1).isEnabled = false

        val fab = findViewById<View>(R.id.fab)
        fab.setOnClickListener {
            clearBackStack()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frame_layout, AllProductsFragment(), "all_products_fragment")
            transaction.setTransition(TRANSIT_FRAGMENT_OPEN)
            transaction.addToBackStack(null)
            transaction.commit()

        }
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    clearBackStack()
                    removeAllProductsFragment()
                    loadFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.profileFragment -> {
                    clearBackStack()
                    removeAllProductsFragment()
                    loadFragment(ProfileFragment())
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }

        bottomNavigationView.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    clearBackStack()
                }
                R.id.profileFragment -> {
                    clearBackStack()
                }
            }
        }
        tokenValidation()
        checkLatsOrderStatus()
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame_layout, fragment)
        transaction.setTransition(TRANSIT_FRAGMENT_OPEN)
        transaction.commit()
    }

    private fun clearBackStack() {
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
    }

    private fun removeAllProductsFragment() {
        val currentFragment = supportFragmentManager.findFragmentByTag("all_products_fragment")
        if (currentFragment != null) {
            supportFragmentManager.commit {
                remove(currentFragment)
            }
        }
    }

    override fun onBackPressed() {
        if (fm.backStackEntryCount == 0) {
            if (backPressedTime + 3000 > System.currentTimeMillis()) {
                super.onBackPressed()
                finish()
            } else
                Toast.makeText(this, getString(R.string.app_exit), Toast.LENGTH_SHORT)
                    .show()
            backPressedTime = System.currentTimeMillis()
        } else
        super.onBackPressed()
    }

    private fun tokenValidation() {
        val sharedPref = getSharedPreferences("access_token", Context.MODE_PRIVATE) ?: return
        val token = "bearer " + sharedPref.getString(Constants.ACCESS_TOKEN, Constants.NO_TOKEN)

        viewModel.getRefreshedToken(token)
        viewModel.refreshToken.observe(this) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { token ->
                        val sharedPreferences: SharedPreferences = getSharedPreferences(
                            "access_token",
                            Context.MODE_PRIVATE
                        )
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("access_token", token.access_token)
                        editor.apply()
                        println("token {${token.access_token}}")
                    }
                }
                is Resource.Error -> {
                    it.message?.let { message ->

                    }
                }
                is Resource.Loading -> {

                }
            }
        }
    }

    private fun checkLatsOrderStatus() {
        val sharedPref = getSharedPreferences("last_order_id", Context.MODE_PRIVATE) ?: return
        val lastOrderId = sharedPref.getString("last_order_id", "0")?.toInt()
        if (lastOrderId != 0) {
            viewModel.getOrderStatus(lastOrderId!!)
            viewModel.orderStatus.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { order ->
                            if (order.data.isNotEmpty())
                            if ( order.data[0].post_status == "wc-completed") {
                                val orderId = getSharedPreferences("last_order_id", Context.MODE_PRIVATE)
                                orderId.edit().clear().apply()
                                cartViewModel.deleteAllCartItems()
                            }
                        }
                    }
                    is Resource.Error -> {
                        it.message?.let { message ->

                        }
                    }
                    is Resource.Loading -> {

                    }
                }
            }
        }
    }

}
