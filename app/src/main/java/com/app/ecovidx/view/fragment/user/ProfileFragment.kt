package com.app.ecovidx.view.fragment.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.ecovidx.R
import com.app.ecovidx.databinding.FragmentProfileBinding
import com.app.ecovidx.utils.Resource
import com.app.ecovidx.view.activity.HomeActivity
import com.app.ecovidx.view.activity.MainActivity
import com.app.ecovidx.view.fragment.cart.CartFragment
import com.app.ecovidx.viewmodel.CartViewModel
import com.app.ecovidx.viewmodel.HomeViewModel

class ProfileFragment: Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    lateinit var cartViewModel: CartViewModel
    lateinit var homeViewModel: HomeViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentProfileBinding = FragmentProfileBinding.bind(view)
        binding = fragmentProfileBinding

        homeViewModel = (activity as HomeActivity).viewModel
        cartViewModel = (activity as HomeActivity).cartViewModel

        binding.toolbarProfile.backView.visibility = View.GONE
        binding.toolbarProfile.title.text = getString(R.string.profile)
        binding.logout.setOnClickListener {
            val sharedPref = requireActivity().getSharedPreferences("access_token", Context.MODE_PRIVATE)
                ?: return@setOnClickListener
            sharedPref.edit().clear().apply()

            cartViewModel.deleteAllCartItems()

            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        getUserDetailsResponse()
        binding.accountDetails.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.fragment_profile_container, AccountDetailsFragment(), "fragment_prof_to_acc")
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                addToBackStack(null)
            }
        }
        binding.orders.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.fragment_profile_container, OrderHistoryFragment(), "fragment_prof_to_orders")
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                addToBackStack(null)
            }
        }
    }

    private fun getUserDetailsResponse() {
        homeViewModel.userDetails.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {

                       binding.userMail.text = it.user_email
                       binding.userName.text = it.first_name
                    }
                }
                is Resource.Error -> {
                    it.message?.let { message ->

                    }
                }
                is Resource.Loading -> {

                }
            }
        })
    }

}