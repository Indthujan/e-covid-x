package com.app.ecovidx.model

data class Product(
    val image_url: String,
    val price: String,
    val product_id: Int,
    val product_title: String,
    val post_content: String,
    val message: String
)