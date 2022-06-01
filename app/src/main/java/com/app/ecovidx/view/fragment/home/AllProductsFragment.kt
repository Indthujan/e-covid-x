package com.app.ecovidx.view.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.ecovidx.R
import com.app.ecovidx.adapter.ProductsByCategoryAdapter
import com.app.ecovidx.data.model.Product
import com.app.ecovidx.databinding.FragmentAllProductsBinding
import com.app.ecovidx.utils.Resource
import com.app.ecovidx.view.activity.HomeActivity
import com.app.ecovidx.viewmodel.HomeViewModel

class AllProductsFragment : Fragment(R.layout.fragment_all_products), ProductsByCategoryAdapter.ClickItemListener {

    private lateinit var binding: FragmentAllProductsBinding
    lateinit var viewModel: HomeViewModel
    private lateinit var productsByCategoryAdapter: ProductsByCategoryAdapter
    private var itemsList = ArrayList<Product>()
    private var offset = 0
    private var limit = 21
    private var totalCount = 0
    private var loadData = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentAllProductsBinding = FragmentAllProductsBinding.bind(view)
        binding = fragmentAllProductsBinding

        viewModel = (activity as HomeActivity).viewModel
        viewModel.allProducts.value = null
        getProductsByCategoryResponse()
        viewModel.getAllProducts(offset, limit)

        binding.allProductsToolbar.backView.visibility = View.GONE
        binding.allProductsToolbar.title.text = getString(R.string.all_products)

        binding.rvAllProducts.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == totalCount - 1) {
                    if (totalCount < 150) {
                        offset += 21
                        loadData = true
                        viewModel.getAllProducts(offset, limit)
                    }
                }
            }
        })
    }

    private fun getProductsByCategoryResponse() {
        viewModel.allProducts.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { productList ->
                        totalCount += productList.size
                        val oldCount = itemsList.size
                        itemsList.clear()
                        itemsList.addAll(productList)
                        if (productList.isNotEmpty() && !loadData) {
                            setUpProductsByCategoryRecyclerView(ArrayList(productList))
                        } else {
                            productsByCategoryAdapter.addData(itemsList)
                            productsByCategoryAdapter.notifyDataSetChanged()
                            productsByCategoryAdapter.notifyItemRangeInserted(
                                oldCount,
                                itemsList.size
                            )
                            loadData = false
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

    private fun setUpProductsByCategoryRecyclerView(list: ArrayList<Product>) {
        productsByCategoryAdapter = ProductsByCategoryAdapter(list, this)
        binding.rvAllProducts.apply {
            adapter = productsByCategoryAdapter
            layoutManager = GridLayoutManager(activity, 3, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onProductItemClicked(product: Product) {
        val bundle = Bundle()
        bundle.putParcelable("product", product)

        val fragment = ProductDetailFragment()
        fragment.arguments = bundle

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.product_all_container, fragment, "fragment_all_to_detail")
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}