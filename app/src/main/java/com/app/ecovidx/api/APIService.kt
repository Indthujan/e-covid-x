package com.app.ecovidx.api

import com.app.ecovidx.data.model.*
import com.app.ecovidx.data.model.order.Data
import com.app.ecovidx.data.model.order.Order
import com.app.ecovidx.data.model.order.OrderResponse
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @POST("login")
    suspend fun postLoginCredentials(
        @Body body: Login
    ): Response<AuthResponse>

    @POST("register")
    suspend fun userRegistration(
        @Body body: Register
    ): Response<RegisterResponse>

    @POST("password_reset")
    suspend fun verifyMailToPasswordReset(
        @Body body: Password
    ): Response<Password>

    @POST("password_reset_otp")
    suspend fun enterOTPtoPasswordReset(
        @Body body: Password
    ): Response<Password>

    @POST("password_reset_new")
    suspend fun confirmPasswordReset(
        @Body body: Password
    ): Response<Password>

    @GET("category")
    suspend fun getCategories(): Response<List<Category>>

    @GET("home")
    suspend fun getHomeProductList(): Response<ProductType>

    @GET("product/{id}/category/{offset}/offset/{limit}/limit")
    suspend fun getProductsByCategoryID(
        @Path("id") id: Int,
        @Path("offset") offset: Int,
        @Path("limit") limit: Int
    ): Response<List<Product>>

    @GET("product/{offset}/offset/{limit}/limit")
    suspend fun getAllProducts(
        @Path("offset") offset: Int,
        @Path("limit") limit: Int
    ): Response<List<Product>>

    @GET("user")
    suspend fun getUserDetails(
        @Header("Authorization") token: String
    ): Response<Profile>

    @POST("user/update")
    suspend fun updateUserDetails(
        @Header("Authorization") token: String,
        @Body body: Info
    ): Response<Info>

    @GET("home/sliders")
    suspend fun getSliderBanners(): Response<List<Slider>>

    @GET("product/{query}/search/{offset}/offset/{limit}/limit")
    suspend fun getSearchedProducts(
        @Path("query") query: String,
        @Path("offset") offset: Int,
        @Path("limit") limit: Int
    ): Response<List<Product>>

    @POST("refresh")
    suspend fun refreshToken(
        @Header("Authorization") token: String
    ): Response<Token>

    @POST("order/create")
    suspend fun createOrder(
        @Header("Authorization") token: String,
        @Body body: Order
    ): Response<OrderResponse>

    @GET("order/all")
    suspend fun getAllOrders(
        @Header("Authorization") token: String
    ): Response<Orders>

    @GET("order/{id}/status")
    suspend fun getOrderStatus(
        @Path("id") offset: Int
    ): Response<OrderResponse>

}