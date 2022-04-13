package com.app.ecovidx.api

import com.app.ecovidx.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

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

}