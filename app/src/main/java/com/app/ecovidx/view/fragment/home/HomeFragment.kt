package com.app.ecovidx.view.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.ecovidx.R
import com.app.ecovidx.adapter.CategoryAdapter
import com.app.ecovidx.adapter.ProductTypeAdapter
import com.app.ecovidx.databinding.FragmentHomeBinding
import com.app.ecovidx.data.model.Category
import com.app.ecovidx.data.model.Product
import com.app.ecovidx.utils.Resource
import com.app.ecovidx.view.activity.HomeActivity
import com.app.ecovidx.viewmodel.HomeViewModel

class HomeFragment : Fragment(R.layout.fragment_home), CategoryAdapter.ClickItemListener,
    ProductTypeAdapter.ClickItemListener {

    lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var productTypeAdapter: ProductTypeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding

        viewModel = (activity as HomeActivity).viewModel

        getCategoriesResponse()
        getProductListResponse()

    }

    private fun getProductListResponse() {
        viewModel.productListResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { productType ->

                        if (productType.best_deals.isNotEmpty()) {
                            setUpBestDealsRecyclerView(productType.best_deals)
                        }
                        if (productType.latest_products.isNotEmpty()) {
                            setUpLatestProductsRecyclerView(productType.latest_products)
                        }
                        if (productType.special_offers.isNotEmpty()) {
                            setUpSpecialOffersRecyclerView(productType.special_offers)
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
        categoryAdapter = CategoryAdapter(list, this)
        binding.rvCategories.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setUpBestDealsRecyclerView(list: List<Product>) {
        productTypeAdapter = ProductTypeAdapter(list, this)
        binding.rvProdcutType.apply {
            adapter = productTypeAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setUpLatestProductsRecyclerView(list: List<Product>) {
        productTypeAdapter = ProductTypeAdapter(list, this)
        binding.rvNewOffers.apply {
            adapter = productTypeAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setUpSpecialOffersRecyclerView(list: List<Product>) {
        productTypeAdapter = ProductTypeAdapter(list, this)
        binding.rvSpclOfrs.apply {
            adapter = productTypeAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onCategoryItemClicked(category: Category) {

        if (category.count > 0) {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToProductsByCategoryFragment(
                    category.term_id,
                    category.name,
                    category.count
                )
            )
        }
    }

    override fun onProductItemClicked(product: Product) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(product)
        )
    }
}