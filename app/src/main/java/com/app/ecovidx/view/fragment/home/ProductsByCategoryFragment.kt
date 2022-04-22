package com.app.ecovidx.view.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.ecovidx.R
import com.app.ecovidx.adapter.ProductsByCategoryAdapter
import com.app.ecovidx.databinding.FragmentProductsbyCategoryBinding
import com.app.ecovidx.data.model.Product
import com.app.ecovidx.utils.Resource
import com.app.ecovidx.view.activity.HomeActivity
import com.app.ecovidx.viewmodel.HomeViewModel
import java.util.ArrayList

class ProductsByCategoryFragment : Fragment(), ProductsByCategoryAdapter.ClickItemListener {

    private lateinit var binding: FragmentProductsbyCategoryBinding
    lateinit var viewModel: HomeViewModel
    private lateinit var productsByCategoryAdapter: ProductsByCategoryAdapter
    private var itemsList = ArrayList<Product>()
    private var offset = 0
    private var limit = 21
    private var totalCount = 0
    private var loadData = false
    private var savedViewInstance: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  if (savedViewInstance != null) {
            savedViewInstance
        } else {
            savedViewInstance =
                inflater.inflate(R.layout.fragment_productsby_category, container, false)
            savedViewInstance
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentProductsCategoryBinding = FragmentProductsbyCategoryBinding.bind(view)
        binding = fragmentProductsCategoryBinding
        binding.fpcToolbar.title.text = arguments!!.getString("categoryName")
        viewModel = (activity as HomeActivity).viewModel
        viewModel.productsByCategory.value = null
        getProductsByCategoryResponse()
        viewModel.getProductsByCategoryID(arguments!!.getInt("categoryID"), offset, limit)

        binding.fpcToolbar.backView.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.rvProductsOfCategories.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == totalCount - 1) {
                    if (totalCount < arguments!!.getInt("categoryCount")) {
                        offset += 21
                        loadData = true
                        viewModel.getProductsByCategoryID(arguments!!.getInt("categoryID"), offset, limit)
                    }
                }
            }
        })
    }

    private fun getProductsByCategoryResponse() {
        viewModel.productsByCategory.observe(viewLifecycleOwner, Observer {
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
        binding.rvProductsOfCategories.apply {
            adapter = productsByCategoryAdapter
            layoutManager = GridLayoutManager(activity, 3, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onProductItemClicked(product: Product) {
//        findNavController().navigate(
//            ProductsByCategoryFragmentDirections.actionProductsByCategoryFragmentToProductDetailFragment(product)
//        )

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.all_products_container, AllProductsFragment(), "fragment_all")
        transaction.addToBackStack(null)
        transaction.commit()
    }
}