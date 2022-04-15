package com.app.ecovidx.view.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.app.ecovidx.R
import com.app.ecovidx.adapter.ProductsByCategoryAdapter
import com.app.ecovidx.databinding.FragmentProductsbyCategoryBinding
import com.app.ecovidx.model.Product
import com.app.ecovidx.utils.Resource
import com.app.ecovidx.view.activity.HomeActivity
import com.app.ecovidx.viewmodel.HomeViewModel

class ProductsByCategoryFragment : Fragment(R.layout.fragment_productsby_category) {

    private lateinit var binding: FragmentProductsbyCategoryBinding
    private val args: ProductsByCategoryFragmentArgs by navArgs()
    lateinit var viewModel: HomeViewModel
    lateinit var productsByCategoryAdapter: ProductsByCategoryAdapter
    private var itemsList: List<Product> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentProductsCategoryBinding = FragmentProductsbyCategoryBinding.bind(view)
        binding = fragmentProductsCategoryBinding
        binding.toolbar.title.text = args.categoryName

        viewModel = (activity as HomeActivity).viewModel
        viewModel.productsByCategory.value = null
        getProductsByCategoryResponse()
        viewModel.getProductsByCategoryID(args.categoryId, 0, 20)

        binding.toolbar.backView.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getProductsByCategoryResponse() {
        viewModel.productsByCategory.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { productList ->

                        if (productList.isNotEmpty()) {
                            setUpProductsByCategoryRecyclerView(productList)
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

    private fun setUpProductsByCategoryRecyclerView(list: List<Product>) {
        productsByCategoryAdapter = ProductsByCategoryAdapter(list)
        binding.rvProductsOfCategories.apply {
            adapter = productsByCategoryAdapter
            layoutManager = GridLayoutManager(activity,3, GridLayoutManager.VERTICAL, false)
        }
    }
}