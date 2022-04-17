package com.app.ecovidx.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecovidx.data.model.*
import com.app.ecovidx.utils.Resource
import com.app.ecovidx.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    val loginResponse: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    val registerResponse: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    val verifyMail: MutableLiveData<Resource<Password>> = MutableLiveData()
    val verifyOtp: MutableLiveData<Resource<Password>> = MutableLiveData()
    val passwordReset: MutableLiveData<Resource<Password>> = MutableLiveData()

    fun postLoginCredentials(credentials: Login) = viewModelScope.launch {
        loginResponse.postValue(Resource.Loading())
        val response = authRepository.postLoginCredentials(credentials)
        loginResponse.postValue(handleLoginResponse(response))
    }

    private fun handleLoginResponse(response: Response<AuthResponse>): Resource<AuthResponse> {

        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun userRegistration(userdata: Register) = viewModelScope.launch {
        registerResponse.postValue(Resource.Loading())
        val response = authRepository.userRegistration(userdata)
        registerResponse.postValue(handleUserRegistration(response))
    }

    private fun handleUserRegistration(response: Response<RegisterResponse>): Resource<RegisterResponse> {

        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun verifyMail(userdata: Password) = viewModelScope.launch {
        verifyMail.postValue(Resource.Loading())
        val response = authRepository.verifyMailToPasswordReset(userdata)
        verifyMail.postValue(handleMailVerification(response))
    }

    private fun handleMailVerification(response: Response<Password>): Resource<Password> {

        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun verifyOTP(userdata: Password) = viewModelScope.launch {
        verifyOtp.postValue(Resource.Loading())
        val response = authRepository.enterOTPtoPasswordReset(userdata)
        verifyOtp.postValue(handleOTPVerification(response))
    }

    private fun handleOTPVerification(response: Response<Password>): Resource<Password> {

        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun passwordReset(userdata: Password) = viewModelScope.launch {
        passwordReset.postValue(Resource.Loading())
        val response = authRepository.confirmPasswordReset(userdata)
        passwordReset.postValue(handlePasswordReset(response))
    }

    private fun handlePasswordReset(response: Response<Password>): Resource<Password> {

        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}