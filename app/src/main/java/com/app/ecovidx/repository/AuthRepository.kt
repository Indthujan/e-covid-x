package com.app.ecovidx.repository

import com.app.ecovidx.api.RetrofitInstance
import com.app.ecovidx.model.Login
import com.app.ecovidx.model.Password
import com.app.ecovidx.model.Register

class AuthRepository {

    suspend fun postLoginCredentials(credentials : Login) =
        RetrofitInstance.api.postLoginCredentials(credentials)

    suspend fun userRegistration(userData : Register) =
        RetrofitInstance.api.userRegistration(userData)

    suspend fun verifyMailToPasswordReset(userData : Password) =
        RetrofitInstance.api.verifyMailToPasswordReset(userData)

    suspend fun enterOTPtoPasswordReset(userData : Password) =
        RetrofitInstance.api.enterOTPtoPasswordReset(userData)

    suspend fun confirmPasswordReset(userData : Password) =
        RetrofitInstance.api.confirmPasswordReset(userData)

}