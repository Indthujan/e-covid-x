package com.app.ecovidx.data.model.order

data class OrderData(
    val billing_details: List<BillingDetail>,
    val line_items: List<LineItem>,
    val shipping_details: List<ShippingDetail>
)