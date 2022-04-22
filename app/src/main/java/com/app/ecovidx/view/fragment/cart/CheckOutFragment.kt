package com.app.ecovidx.view.fragment.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.ecovidx.R
import com.app.ecovidx.adapter.CheckOutAdapter
import com.app.ecovidx.databinding.FragmentCheckoutBinding
import com.app.ecovidx.db.entities.Cart
import com.app.ecovidx.view.activity.HomeActivity
import com.app.ecovidx.viewmodel.CartViewModel

class CheckOutFragment : Fragment(R.layout.fragment_checkout) {

    lateinit var binding: FragmentCheckoutBinding
    lateinit var viewModel: CartViewModel
    private var totalAmount = 0.00

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentCheckoutBinding = FragmentCheckoutBinding.bind(view)
        binding = fragmentCheckoutBinding

        viewModel = (activity as HomeActivity).cartViewModel
        val adapter = CheckOutAdapter(listOf())

        binding.rvCheckOutItems.layoutManager = LinearLayoutManager(activity)
        binding.rvCheckOutItems.adapter = adapter

        viewModel.getAllShoppingItems().observe(viewLifecycleOwner, Observer {

            if (it != null) {
                adapter.items = it
                adapter.notifyDataSetChanged()
                checkoutSum(it)
            }
        })
    }

    private fun checkoutSum(list: List<Cart>) {
        for (i in list) {
            totalAmount += i.price * i.quantity
        }
        binding.checkoutAmount.text = getString(R.string.amount_symbol, totalAmount)
        binding.totalAmount.text = getString(R.string.amount_symbol, totalAmount)
    }
}
