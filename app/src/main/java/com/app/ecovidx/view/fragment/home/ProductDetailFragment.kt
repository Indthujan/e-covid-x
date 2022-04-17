package com.app.ecovidx.view.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.app.ecovidx.R
import com.app.ecovidx.databinding.FragmentProductDetailBinding
import com.app.ecovidx.data.model.Product
import com.bumptech.glide.Glide

class ProductDetailFragment: Fragment(R.layout.fragment_product_detail) {

    lateinit var binding: FragmentProductDetailBinding
    lateinit var product: Product
    private val args: ProductDetailFragmentArgs by navArgs()
    private var quantity = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentProductDetailBinding = FragmentProductDetailBinding.bind(view)
        binding = fragmentProductDetailBinding

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
    }
}