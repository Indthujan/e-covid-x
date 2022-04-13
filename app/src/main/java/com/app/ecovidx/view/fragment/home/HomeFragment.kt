package com.app.ecovidx.view.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.ecovidx.R
import com.app.ecovidx.adapter.CategoryAdapter
import com.app.ecovidx.adapter.ProductTypeAdapter
import com.app.ecovidx.databinding.FragmentHomeBinding
import com.app.ecovidx.model.Category
import com.app.ecovidx.model.Product
import com.app.ecovidx.model.ProductType
import com.app.ecovidx.utils.Resource
import com.app.ecovidx.view.activity.HomeActivity
import com.app.ecovidx.viewmodel.HomeViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var productTypeAdapter: ProductTypeAdapter
    private var itemsList: List<Category> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding

        viewModel = (activity as HomeActivity).viewModel

        getCategoriesResponse()
        getProductListResponse()

        viewModel.getCategories()
        viewModel.getHomeProductList()
    }

    private fun getProductListResponse() {
        viewModel.productListResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {
                        setUpBestDealsRecyclerView(it.best_deals)
                        setUpNwOfrsRecyclerView(it.best_deals)
                        setUpSpclOfrsRecyclerView(it.best_deals)
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
        })
    }

    private fun getCategoriesResponse() {
        viewModel.categoryResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {

                        setUpCategoryRecyclerView(it)
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
        })

    }

    private fun setUpCategoryRecyclerView(list: List<Category>) {
        categoryAdapter = CategoryAdapter(list)
        binding.rvCategories.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setUpBestDealsRecyclerView(list: List<Product>) {
        productTypeAdapter = ProductTypeAdapter(list)
        binding.rvProdcutType.apply {
            adapter = productTypeAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setUpNwOfrsRecyclerView(list: List<Product>) {
        productTypeAdapter = ProductTypeAdapter(list)
        binding.rvNewOffers.apply {
            adapter = productTypeAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setUpSpclOfrsRecyclerView(list: List<Product>) {
        productTypeAdapter = ProductTypeAdapter(list)
        binding.rvSpclOfrs.apply {
            adapter = productTypeAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }


}