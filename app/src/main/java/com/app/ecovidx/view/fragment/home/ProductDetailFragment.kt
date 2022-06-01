package com.app.ecovidx.view.fragment.home

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.core.text.HtmlCompat.*
import androidx.core.text.htmlEncode
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.app.ecovidx.R
import com.app.ecovidx.databinding.FragmentProductDetailBinding
import com.app.ecovidx.data.model.Product
import com.app.ecovidx.db.entities.Cart
import com.app.ecovidx.view.activity.HomeActivity
import com.app.ecovidx.view.fragment.cart.CartFragment
import com.app.ecovidx.view.fragment.cart.CheckOutFragment
import com.app.ecovidx.viewmodel.CartViewModel
import com.bumptech.glide.Glide

class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    lateinit var binding: FragmentProductDetailBinding
    lateinit var viewModel: CartViewModel
    lateinit var product: Product
    private var quantity = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentProductDetailBinding = FragmentProductDetailBinding.bind(view)
        binding = fragmentProductDetailBinding

        viewModel = (activity as HomeActivity).cartViewModel
        product = requireArguments().getParcelable("product")!!
        setUpLayout()
        binding.pdToolbar.backView.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.buyNow.setOnClickListener {
            buyOrAddProduct()
            requireActivity().supportFragmentManager.commit {
                replace(R.id.detail_to_cart_container, CartFragment(), "fragment_detail_to_cart")
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                addToBackStack(null)
            }
        }
        if (product.post_content.isNotEmpty()) {
            binding.productDesc.text = fromHtml(product.post_content, FROM_HTML_MODE_LEGACY).toString()
        }
        binding.priceTxt.text = getString(R.string.pound, product.price)
    }

    private fun setUpLayout() {
        binding.productTitle.text = product.product_title
        Glide.with(this).load(product.image_url).into(binding.productImg)

        binding.ivPlus.setOnClickListener {
            quantity += 1
            binding.quantity.text = quantity.toString()
        }

        binding.ivMinus.setOnClickListener {
            quantity = (quantity - 1).coerceAtLeast(1)
            binding.quantity.text = quantity.toString()
        }

        binding.addToCart.setOnClickListener {
            buyOrAddProduct()
        }
    }

    private fun buyOrAddProduct() {
        viewModel.upsert(
            Cart(
                product.product_id,
                product.product_title,
                quantity,
                product.image_url,
                product.price.toDouble()
            )
        )
        Toast.makeText(context, "Added to Basket", Toast.LENGTH_SHORT).show()
    }
}
