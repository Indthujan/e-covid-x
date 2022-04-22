package com.app.ecovidx.view.fragment.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.app.ecovidx.R
import com.app.ecovidx.databinding.FragmentProductDetailBinding
import com.app.ecovidx.data.model.Product
import com.app.ecovidx.db.entities.Cart
import com.app.ecovidx.view.activity.HomeActivity
import com.app.ecovidx.viewmodel.CartViewModel
import com.bumptech.glide.Glide

class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    lateinit var binding: FragmentProductDetailBinding
    lateinit var viewModel: CartViewModel
    lateinit var product: Product
    private val args: ProductDetailFragmentArgs by navArgs()
    private var quantity = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentProductDetailBinding = FragmentProductDetailBinding.bind(view)
        binding = fragmentProductDetailBinding

        viewModel = (activity as HomeActivity).cartViewModel

        setUpLayout()

    }

    private fun setUpLayout() {

        binding.productTitle.text = args.product.product_title
        Glide.with(this).load(args.product.image_url).into(binding.productImg)

        binding.ivPlus.setOnClickListener {
            quantity += 1
            binding.quantity.text = quantity.toString()
        }

        binding.ivMinus.setOnClickListener {
            quantity = (quantity - 1).coerceAtLeast(0)
            binding.quantity.text = quantity.toString()
        }

        binding.addToCart.setOnClickListener {

            if (quantity > 0) {
                viewModel.upsert(
                    Cart(
                        args.product.product_title,
                        quantity,
                        args.product.image_url,
                        args.product.price.toDouble()
                    )
                )
                Toast.makeText(context, "Added to Basket", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(context, "Select your quantity", Toast.LENGTH_SHORT).show()

        }
    }
}
