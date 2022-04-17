package com.app.ecovidx.repository

import com.app.ecovidx.api.RetrofitInstance

class HomeRepository {

    suspend fun getCategories() = RetrofitInstance.api.getCategories()

    suspend fun getHomeProductList() = RetrofitInstance.api.getHomeProductList()

    suspend fun getProductsByCategoryID(id: Int, offset: Int, limit: Int) =
        RetrofitInstance.api.getProductsByCategoryID(id, offset, limit)

    suspend fun getAllProducts(offset: Int, limit: Int) =
        RetrofitInstance.api.getAllProducts(offset, limit)
}