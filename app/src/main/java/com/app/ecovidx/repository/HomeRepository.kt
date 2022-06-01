package com.app.ecovidx.repository

import com.app.ecovidx.api.RetrofitInstance
import com.app.ecovidx.data.model.Info
import com.app.ecovidx.data.model.order.Order

class HomeRepository {

    suspend fun getCategories() = RetrofitInstance.api.getCategories()

    suspend fun getHomeProductList() = RetrofitInstance.api.getHomeProductList()

    suspend fun getProductsByCategoryID(id: Int, offset: Int, limit: Int) =
        RetrofitInstance.api.getProductsByCategoryID(id, offset, limit)

    suspend fun getAllProducts(offset: Int, limit: Int) =
        RetrofitInstance.api.getAllProducts(offset, limit)

    suspend fun getUserDetails(token: String) =
        RetrofitInstance.api.getUserDetails(token)

    suspend fun updateUserDetails(token: String, info: Info) =
        RetrofitInstance.api.updateUserDetails(token, info)

    suspend fun getSliderBanners() = RetrofitInstance.api.getSliderBanners()

    suspend fun getSearchedProducts(query: String, offset: Int, limit: Int) =
        RetrofitInstance.api.getSearchedProducts(query, offset, limit)

    suspend fun refreshToken(token: String) =
        RetrofitInstance.api.refreshToken(token)

    suspend fun createOrder(token: String, order: Order) =
        RetrofitInstance.api.createOrder(token, order)

    suspend fun getAllOrders(token: String) = RetrofitInstance.api.getAllOrders(token)

    suspend fun getOrderStatus(id: Int) = RetrofitInstance.api.getOrderStatus(id)
}