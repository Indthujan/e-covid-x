package com.app.ecovidx.view.fragment.user

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.ecovidx.R
import com.app.ecovidx.adapter.OrderHistoryAdapter
import com.app.ecovidx.adapter.ProductTypeAdapter
import com.app.ecovidx.data.model.OrderList
import com.app.ecovidx.data.model.Product
import com.app.ecovidx.databinding.FragmentOrderHistoryBinding
import com.app.ecovidx.utils.Constants
import com.app.ecovidx.utils.Resource
import com.app.ecovidx.view.activity.HomeActivity
import com.app.ecovidx.viewmodel.HomeViewModel

class OrderHistoryFragment: Fragment(R.layout.fragment_order_history) {

    private lateinit var binding: FragmentOrderHistoryBinding
    lateinit var viewModel: HomeViewModel
    lateinit var orderAdapter: OrderHistoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentOrderHistoryBinding = FragmentOrderHistoryBinding.bind(view)
        binding = fragmentOrderHistoryBinding

        viewModel = (activity as HomeActivity).viewModel
        binding.ordersToolbar.title.text = "Order History"
        binding.ordersToolbar.backView.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        val sharedPref =
            requireActivity().getSharedPreferences("access_token", Context.MODE_PRIVATE) ?: return
        val token = "bearer " + sharedPref.getString(Constants.ACCESS_TOKEN, Constants.NO_TOKEN)

        viewModel.getAllOrders(token)
        viewModel.getOrdersResponse.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    it.data?.let { orderList ->
                        setUpRecyclerView(orderList.orders)

                    }
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }
    }

    private fun setUpRecyclerView(list: List<OrderList>) {
        orderAdapter = OrderHistoryAdapter(list)
        binding.rvAllOrders.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
    }

}