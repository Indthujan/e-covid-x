package com.app.ecovidx.view.fragment.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.app.ecovidx.R
import com.app.ecovidx.adapter.CategoryAdapter
import com.app.ecovidx.adapter.ProductTypeAdapter
import com.app.ecovidx.adapter.SliderAdapter
import com.app.ecovidx.databinding.FragmentHomeBinding
import com.app.ecovidx.data.model.Category
import com.app.ecovidx.data.model.Product
import com.app.ecovidx.data.model.Slider
import com.app.ecovidx.utils.Resource
import com.app.ecovidx.view.activity.HomeActivity
import com.app.ecovidx.view.fragment.cart.CartFragment
import com.app.ecovidx.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home), CategoryAdapter.ClickItemListener,
    ProductTypeAdapter.ClickItemListener {

    lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productTypeAdapter: ProductTypeAdapter
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var mViewPager: ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding

        viewModel = (activity as HomeActivity).viewModel

        getSliderResponse()
        getCategoriesResponse()
        getProductListResponse()

        binding.toolbar.shoppingCart.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.home_to_cart_container, CartFragment(), "fragment_home_to_cart")
                setTransition(TRANSIT_FRAGMENT_OPEN)
                addToBackStack(null)
            }
        }

        binding.toolbar.search.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.home_to_product_detail_container, SearchFragment(), "home_to_search")
                setTransition(TRANSIT_FRAGMENT_OPEN)
                addToBackStack(null)
            }
        }
    }

    private fun getProductListResponse() {
        viewModel.productListResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { productType ->
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.visibility = View.GONE
                        if (productType.best_deals.isNotEmpty()) setUpBestDealsRecyclerView(productType.best_deals)
                        if (productType.latest_products.isNotEmpty()) setUpLatestProductsRecyclerView(productType.latest_products)
                        if (productType.special_offers.isNotEmpty()) setUpSpecialOffersRecyclerView(productType.special_offers)
                        binding.bdLayout.visibility = View.VISIBLE
                        binding.noLayout.visibility = View.VISIBLE
                        binding.soLayout.visibility = View.VISIBLE
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
                        binding.categoryLayout.alpha = 0f
                        binding.categoryLayout.visibility = View.VISIBLE
                        val shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
                        binding.categoryLayout.animate()
                            .alpha(1f)
                            .setDuration(shortAnimationDuration.toLong())
                            .setListener(null)
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

    private fun getSliderResponse() {
        viewModel.sliderResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {
                        setBannersToAdapter(it)
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

    private fun setBannersToAdapter(list: List<Slider>) {
        mViewPager = binding.sliderPager
        sliderAdapter = SliderAdapter(list)

        mViewPager.apply {
            adapter = sliderAdapter
        }

        binding.dotsIndicator.setViewPager2(mViewPager)

        lifecycleScope.launch {
            while (true) {
                for (i in 0..list.size) {
                    delay(2000)
                    if (i == 0)
                        mViewPager.setCurrentItem(i, false)
                    else
                        mViewPager.setCurrentItem(i, true)
                }
            }
        }
    }

    override fun onCategoryItemClicked(category: Category) {
        val bundle = Bundle()
        bundle.putInt("categoryID", category.term_id)
        bundle.putInt("categoryCount", category.count)
        bundle.putString("categoryName", category.name)

        val fragment = ProductsByCategoryFragment()
        fragment.arguments = bundle

        val currentFragment =
            requireActivity().supportFragmentManager.findFragmentByTag("fragment_home_to_category")
        if (currentFragment != null) {
            requireActivity().supportFragmentManager.commit {
                remove(currentFragment)
            }
        }
        requireActivity().supportFragmentManager.commit {
            replace(R.id.cate_products_container, fragment, "fragment_home_to_category")
            setTransition(TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
        }
    }

    override fun onProductItemClicked(product: Product) {
        val bundle = Bundle()
        bundle.putParcelable("product", product)

        val fragment = ProductDetailFragment()
        fragment.arguments = bundle

        requireActivity().supportFragmentManager.commit {
            replace(R.id.home_to_product_detail_container, fragment, "home_to_product_detail")
            setTransition(TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
        }
    }
}