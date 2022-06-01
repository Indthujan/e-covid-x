package com.app.ecovidx.view.fragment.cart

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.app.ecovidx.R
import com.app.ecovidx.data.model.Profile
import com.app.ecovidx.data.model.order.*
import com.app.ecovidx.databinding.FragmentBillingBinding
import com.app.ecovidx.utils.Constants
import com.app.ecovidx.utils.Resource
import com.app.ecovidx.view.activity.HomeActivity
import com.app.ecovidx.view.fragment.user.AccountDetailsFragment
import com.app.ecovidx.viewmodel.CartViewModel
import com.app.ecovidx.viewmodel.HomeViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class BillingFragment : Fragment(R.layout.fragment_billing) {

    private lateinit var binding: FragmentBillingBinding
    lateinit var viewModel: HomeViewModel
    lateinit var cartViewModel: CartViewModel
    private lateinit var token: String
    private var orderId: Int? = null
    private var billingDetail = mutableListOf<BillingDetail>()
    private var shippingDetail = mutableListOf<ShippingDetail>()
    private var diffShippingDetail = mutableListOf<ShippingDetail>()
    private var itemList = mutableListOf<LineItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentBillingBinding = FragmentBillingBinding.bind(view)
        binding = fragmentBillingBinding
        viewModel = (activity as HomeActivity).viewModel
        viewModel.userBillingDetails.value = null
        cartViewModel = (activity as HomeActivity).cartViewModel

        binding.changeAddress.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                textInputLayoutState(isChecked)
            } else
                textInputLayoutState(isChecked)
        }

        val sharedPref =
            requireActivity().getSharedPreferences("access_token", Context.MODE_PRIVATE) ?: return
         token = "bearer " + sharedPref.getString(Constants.ACCESS_TOKEN, Constants.NO_TOKEN)

        userDetailsResponse()

        binding.next.setOnClickListener {
            createOrder(token)
        }
        cartViewModel.getAllShoppingItems().observe(viewLifecycleOwner) { items ->
            if (items != null) {
                for (i in items) {
                    itemList.add(LineItem(i.id, i.quantity))
                }
            }
        }
    }

    private fun userDetailsResponse() {
        viewModel.userBillingDetails.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { profile ->

                        if (profile.status != "Token is Expired")
                            loadUserDetails(profile)
                        binding.progressBar.visibility = View.GONE
                    }
                    binding.progressBar.visibility = View.GONE
                }
                is Resource.Error -> {
                    it.message?.let { message ->

                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun loadUserDetails(user: Profile) {

        binding.firstName.setText(user.first_name)
        binding.lastName.setText(user.last_name)
        binding.companyName.setText(user.billing_company)
        binding.townName.setText(user.billing_city)
        binding.postcode.setText(user.billing_postcode)
        binding.phoneNo.setText(user.billing_phone)
        binding.streetAddress.setText(user.billing_address_1)

        if (user.first_name.isNullOrEmpty() || user.last_name.isNullOrEmpty() || user.billing_company.isNullOrEmpty()
            || user.billing_city.isNullOrEmpty() || user.billing_postcode.isNullOrEmpty() || user.billing_phone.isNullOrEmpty()
            || user.billing_address_1.isNullOrEmpty()
        ) {
            showDialog()
        } else {
            billingDetail.add(
                BillingDetail(
                    user.billing_address_1,
                    user.billing_city,
                    "UK",
                    user.user_email,
                    user.first_name,
                    user.last_name,
                    user.billing_phone,
                    user.billing_postcode
                )
            )
            shippingDetail.add(
                ShippingDetail(
                    user.billing_address_1,
                    user.billing_city,
                    "UK",
                    user.first_name,
                    user.last_name,
                    user.billing_phone,
                    user.billing_postcode
                )
            )
        }
    }

    private fun textInputLayoutState(isChecked: Boolean) {

        if (isChecked) {
            binding.firstNameInput.isEnabled = true
            binding.lastNameInput.isEnabled = true
            binding.companyNameInput.isEnabled = true
            binding.phoneNoInput.isEnabled = true
            binding.postcodeInput.isEnabled = true
            binding.townNameInput.isEnabled = true
            binding.streetAddressInput.isEnabled = true
        } else {
            binding.firstNameInput.isEnabled = false
            binding.lastNameInput.isEnabled = false
            binding.companyNameInput.isEnabled = false
            binding.phoneNoInput.isEnabled = false
            binding.postcodeInput.isEnabled = false
            binding.townNameInput.isEnabled = false
            binding.streetAddressInput.isEnabled = false
        }

        binding.firstNameInput.setBoxStrokeColorStateList(ContextCompat.getColorStateList(requireActivity(),R.color.input_edit_enabled)!!)
        binding.lastNameInput.setBoxStrokeColorStateList(ContextCompat.getColorStateList(requireActivity(),R.color.input_edit_enabled)!!)
        binding.companyNameInput.setBoxStrokeColorStateList(ContextCompat.getColorStateList(requireActivity(),R.color.input_edit_enabled)!!)
        binding.phoneNoInput.setBoxStrokeColorStateList(ContextCompat.getColorStateList(requireActivity(),R.color.input_edit_enabled)!!)
        binding.postcodeInput.setBoxStrokeColorStateList(ContextCompat.getColorStateList(requireActivity(),R.color.input_edit_enabled)!!)
        binding.townNameInput.setBoxStrokeColorStateList(ContextCompat.getColorStateList(requireActivity(),R.color.input_edit_enabled)!!)
        binding.streetAddressInput.setBoxStrokeColorStateList(ContextCompat.getColorStateList(requireActivity(),R.color.input_edit_enabled)!!)
    }

    private fun createOrder(token: String) {

        if (binding.changeAddress.isChecked) {
            if (binding.firstName.text.isNullOrEmpty() || binding.lastName.text.isNullOrEmpty() || binding.companyName.text.isNullOrEmpty() ||
                binding.townName.text.isNullOrEmpty() || binding.postcode.text.isNullOrEmpty() || binding.phoneNo.text.isNullOrEmpty() ||
                binding.streetAddress.text.isNullOrEmpty()
            ) {
                showSnackBar()
            } else {
                diffShippingDetail.add(
                    ShippingDetail(
                        binding.firstName.text.toString(),
                        binding.lastName.text.toString(),
                        binding.companyName.text.toString(),
                        binding.townName.text.toString(),
                        binding.postcode.text.toString(),
                        binding.phoneNo.text.toString(),
                        binding.streetAddress.text.toString()
                    )
                )
                viewModel.createOrder(
                    token, Order(
                        OrderData(
                            billingDetail, itemList, diffShippingDetail
                        )
                    )
                )
            }
        } else {
            viewModel.createOrder(
                token, Order(
                    OrderData(
                        billingDetail, itemList, shippingDetail
                    )
                )
            )
        }

        viewModel.orderResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {

                        if (it.sucsss == "ok") {
                            val loadPaymentUrl = Intent(Intent.ACTION_VIEW)
                            loadPaymentUrl.data = Uri.parse(it.payment_url)
                            startActivity(loadPaymentUrl)

                            val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(
                                "last_order_id",
                                Context.MODE_PRIVATE
                            )
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("last_order_id", it.order_id.toString())
                            editor.apply()
                            orderId = it.order_id
                        }
                        binding.progressBar.visibility = View.GONE
                    }
                }
                is Resource.Error -> {
                    it.message?.let { message ->

                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setMessage("Update billing details to place an Order!")
            .setPositiveButton("Update")
            { _, _ ->
                requireActivity().supportFragmentManager.commit {
                    replace(
                        R.id.fragment_bill_to_profile_container,
                        AccountDetailsFragment(),
                        "fragment_prof_to_acc"
                    )
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    addToBackStack(null)
                }
            }
            .setNegativeButton(
                "Cancel"
            ) { dialog, _ ->
                requireActivity().supportFragmentManager.popBackStack()
                dialog.dismiss()
            }
        builder.show()
    }

    private fun showSnackBar() {
        val snackBarView = Snackbar.make(binding.root, "Enter the missing fields!" , Snackbar.LENGTH_LONG)
        val view = snackBarView.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        snackBarView.setBackgroundTint(ContextCompat.getColor(requireContext(),
            R.color.md_red_900))
        snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBarView.show()
    }

    private fun checkOrderStatus() {
        if (orderId != null) {
            viewModel.getOrderStatus(orderId!!)
            viewModel.orderStatus.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { order ->
                            if (order.data[0].post_status == "wc-completed") {
                                val orderId = requireActivity().getSharedPreferences("last_order_id", Context.MODE_PRIVATE)
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

    override fun onResume() {
        super.onResume()
        viewModel.getUserBillingDetails(token)
        checkOrderStatus()
    }
}