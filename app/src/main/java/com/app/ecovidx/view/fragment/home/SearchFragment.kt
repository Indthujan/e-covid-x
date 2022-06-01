package com.app.ecovidx.view.fragment.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.ecovidx.R
import com.app.ecovidx.adapter.ProductsByCategoryAdapter
import com.app.ecovidx.data.model.Product
import com.app.ecovidx.databinding.FragmentSearchBinding
import com.app.ecovidx.utils.Resource
import com.app.ecovidx.view.activity.HomeActivity
import com.app.ecovidx.viewmodel.HomeViewModel
import java.util.*


class SearchFragment : Fragment(R.layout.fragment_search),
    ProductsByCategoryAdapter.ClickItemListener {

    private lateinit var binding: FragmentSearchBinding
    lateinit var viewModel: HomeViewModel
    private lateinit var productsByCategoryAdapter: ProductsByCategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentSearchBinding = FragmentSearchBinding.bind(view)
        binding = fragmentSearchBinding

        viewModel = (activity as HomeActivity).viewModel

        binding.searchToolbar.searchView.requestFocus()
        binding.searchToolbar.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.progressBar.visibility = View.VISIBLE
                binding.searchToolbar.searchView.clearFocus()
                viewModel.getSearchedProducts(query!!, 0, 21)
                getSearchedResponse()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
    }

    private fun getSearchedResponse() {
        viewModel.searchedProducts.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { productList ->

                        setUpSearchRecyclerView(ArrayList(productList))
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

    private fun setUpSearchRecyclerView(list: ArrayList<Product>) {
        productsByCategoryAdapter = ProductsByCategoryAdapter(list, this)
        binding.rvSearchProducts.apply {
            adapter = productsByCategoryAdapter
            layoutManager = GridLayoutManager(activity, 3, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onProductItemClicked(product: Product) {
        val bundle = Bundle()
        bundle.putParcelable("product", product)
        val fragment = ProductDetailFragment()
        fragment.arguments = bundle

        requireActivity().supportFragmentManager.commit {
            replace(R.id.search_container, fragment, "fragment_search_to_detail")
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
        }
    }
}