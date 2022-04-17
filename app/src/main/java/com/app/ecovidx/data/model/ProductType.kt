package com.app.ecovidx.data.model

data class ProductType(
    val best_deals: List<Product>,
    val latest_products: List<Product>,
    val special_offers: List<Product>
)