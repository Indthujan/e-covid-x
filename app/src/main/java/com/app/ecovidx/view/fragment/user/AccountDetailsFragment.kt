package com.app.ecovidx.view.fragment.user

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.app.ecovidx.R
import com.app.ecovidx.data.model.Info
import com.app.ecovidx.data.model.Profile
import com.app.ecovidx.databinding.FragmentAccountDetailsBinding
import com.app.ecovidx.utils.Constants
import com.app.ecovidx.utils.Resource
import com.app.ecovidx.view.activity.HomeActivity
import com.app.ecovidx.view.fragment.cart.BillingFragment
import com.app.ecovidx.viewmodel.HomeViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class AccountDetailsFragment : Fragment(R.layout.fragment_account_details) {

    private lateinit var binding: FragmentAccountDetailsBinding
    lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val accountDetailsBinding = FragmentAccountDetailsBinding.bind(view)
        binding = accountDetailsBinding

        viewModel = (activity as HomeActivity).viewModel

        val sharedPref =
            requireActivity().getSharedPreferences("access_token", Context.MODE_PRIVATE) ?: return
        val token = "bearer " + sharedPref.getString(Constants.ACCESS_TOKEN, Constants.NO_TOKEN)

        viewModel.getUserDetails(token)
        getUserDetailsResponse()
        updateUser(token)

        binding.accToolbar.backView.setOnClickListener {
            val f = requireActivity().supportFragmentManager.findFragmentByTag("fragment_to_billing")
            if (f is BillingFragment)
                f.onResume()
            requireActivity().supportFragmentManager.popBackStack()
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val f = requireActivity().supportFragmentManager.findFragmentByTag("fragment_to_billing")
                if (f is BillingFragment)
                    f.onResume()
                requireActivity().supportFragmentManager.popBackStack()
            }
        })
    }

    private fun getUserDetailsResponse() {
        viewModel.userDetails.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {

                        if (it.status != "Token is Expired")
                            loadUserDetails(it)

                        binding.progressBar.visibility = View.GONE
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

    private fun updateUserDetailsResponse() {
        viewModel.infoResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {

                        if (it.Message == "PostCode Not Valied") {
                            val snackBarView = Snackbar.make(requireView(), "Invalid Postal Code" , Snackbar.LENGTH_LONG)
                            val view = snackBarView.view
                            val params = view.layoutParams as FrameLayout.LayoutParams
                            params.gravity = Gravity.TOP
                            view.layoutParams = params
                            snackBarView.setBackgroundTint(ContextCompat.getColor(requireContext(),
                                R.color.md_red_900))
                            snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
                            snackBarView.show()
                            binding.postcode.requestFocus()
                        } else {
                            val snackBarView = Snackbar.make(requireView(), "Updated" , Snackbar.LENGTH_LONG)
                            val view = snackBarView.view
                            val params = view.layoutParams as FrameLayout.LayoutParams
                            params.gravity = Gravity.TOP
                            view.layoutParams = params
                            snackBarView.setBackgroundTint(ContextCompat.getColor(requireContext(),
                                R.color.md_green_500))
                            snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
                            snackBarView.show()
                        }
                        binding.progressBar.visibility = View.GONE
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

    private fun updateUser(token: String) {
        binding.save.setOnClickListener {
            viewModel.updateUserDetails(
                token,
                Info(
                    binding.firstName.text.toString(),
                    binding.lastName.text.toString(),
                    binding.companyName.text.toString(),
                    binding.townName.text.toString(),
                    binding.postcode.text.toString(),
                    binding.phoneNo.text.toString(),
                    binding.streetAddress.text.toString(),
                    ""
                )
            )
            updateUserDetailsResponse()
        }
    }

    private fun loadUserDetails(user: Profile) {
        binding.firstName.setText(user.first_name)
        binding.lastName.setText(user.last_name)
        binding.companyName.setText(user.billing_company)
        binding.townName.setText(user.billing_city)
        binding.postcode.setText(user.billing_postcode)
        binding.phoneNo.setText(user.billing_phone)
        binding.streetAddress.setText(user.billing_address_1)
    }
}