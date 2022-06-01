package com.app.ecovidx.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecovidx.data.model.*
import com.app.ecovidx.data.model.order.Order
import com.app.ecovidx.data.model.order.OrderResponse
import com.app.ecovidx.repository.HomeRepository
import com.app.ecovidx.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    val categoryResponse: MutableLiveData<Resource<List<Category>>> = MutableLiveData()
    val productListResponse: MutableLiveData<Resource<ProductType>> = MutableLiveData()
    val productsByCategory: MutableLiveData<Resource<List<Product>>> = MutableLiveData()
    val allProducts: MutableLiveData<Resource<List<Product>>> = MutableLiveData()
    val userDetails: MutableLiveData<Resource<Profile>> = MutableLiveData()
    val infoResponse: MutableLiveData<Resource<Info>> = MutableLiveData()
    val sliderResponse: MutableLiveData<Resource<List<Slider>>> = MutableLiveData()
    val searchedProducts: MutableLiveData<Resource<List<Product>>> = MutableLiveData()
    val refreshToken: MutableLiveData<Resource<Token>> = MutableLiveData()
    val orderResponse: MutableLiveData<Resource<OrderResponse>> = MutableLiveData()
    val getOrdersResponse: MutableLiveData<Resource<Orders>> = MutableLiveData()
    val userBillingDetails: MutableLiveData<Resource<Profile>> = MutableLiveData()
    val orderStatus: MutableLiveData<Resource<OrderResponse>> = MutableLiveData()

    init {
        getSliderBanners()
        getCategories()
        getHomeProductList()
    }

    private fun getCategories() = viewModelScope.launch {

        categoryResponse.postValue(Resource.Loading())
        val response = homeRepository.getCategories()
        categoryResponse.postValue(handleResponse(response))
    }

    private fun handleResponse(response: Response<List<Category>>): Resource<List<Category>> {

        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun getHomeProductList() = viewModelScope.launch {

        productListResponse.postValue(Resource.Loading())
        val response = homeRepository.getHomeProductList()
        productListResponse.postValue(handleProductListResponse(response))

    }

    private fun handleProductListResponse(response: Response<ProductType>): Resource<ProductType> {

        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun getProductsByCategoryID(id: Int, offset: Int, limit: Int) = viewModelScope.launch {

        productsByCategory.postValue(Resource.Loading())
        val response = homeRepository.getProductsByCategoryID(id, offset, limit)
        productsByCategory.postValue(handleProductsByCategoryResponse(response))

    }

    private fun handleProductsByCategoryResponse(response: Response<List<Product>>): Resource<List<Product>> {

        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun getAllProducts(offset: Int, limit: Int) = viewModelScope.launch {

        allProducts.postValue(Resource.Loading())
        val response = homeRepository.getAllProducts(offset, limit)
        allProducts.postValue(handleAllProductsResponse(response))

    }

    private fun handleAllProductsResponse(response: Response<List<Product>>): Resource<List<Product>> {

        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun getUserDetails(token: String) = viewModelScope.launch {

        userDetails.postValue(Resource.Loading())
        val response = homeRepository.getUserDetails(token)
        userDetails.postValue(handleUserDetailsResponse(response))

    }

    fun getUserBillingDetails(token: String) = viewModelScope.launch {

        userBillingDetails.postValue(Resource.Loading())
        val response = homeRepository.getUserDetails(token)
        userBillingDetails.postValue(handleUserDetailsResponse(response))

    }


    private fun handleUserDetailsResponse(response: Response<Profile>): Resource<Profile> {

        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun updateUserDetails(token: String, info: Info) = viewModelScope.launch {

        infoResponse.postValue(Resource.Loading())
        val response = homeRepository.updateUserDetails(token, info)
        infoResponse.postValue(handleUpdateUserResponse(response))

    }

    private fun handleUpdateUserResponse(response: Response<Info>): Resource<Info> {

        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun getSliderBanners() = viewModelScope.launch {

        sliderResponse.postValue(Resource.Loading())
        val response = homeRepository.getSliderBanners()
        sliderResponse.postValue(handleSliderResponse(response))
    }

    private fun handleSliderResponse(response: Response<List<Slider>>): Resource<List<Slider>> {

        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun getSearchedProducts(query: String, offset: Int, limit: Int) = viewModelScope.launch {

        searchedProducts.postValue(Resource.Loading())
        val response = homeRepository.getSearchedProducts(query, offset, limit)
        searchedProducts.postValue(handleSearchedProductsResponse(response))

    }

    private fun handleSearchedProductsResponse(response: Response<List<Product>>): Resource<List<Product>> {

        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun getRefreshedToken(token: String) = viewModelScope.launch {

        refreshToken.postValue(Resource.Loading())
        val response = homeRepository.refreshToken(token)
        refreshToken.postValue(handleTokenResponse(response))

    }

    private fun handleTokenResponse(response: Response<Token>): Resource<Token> {

        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun createOrder(token: String, order: Order) = viewModelScope.launch {

        orderResponse.postValue(Resource.Loading())
        val response = homeRepository.createOrder(token, order)
        orderResponse.postValue(handleOrderResponse(response))
    }

    private fun handleOrderResponse(response: Response<OrderResponse>): Resource<OrderResponse> {

        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun getAllOrders(token: String) = viewModelScope.launch {

        getOrdersResponse.postValue(Resource.Loading())
        val response = homeRepository.getAllOrders(token)
        getOrdersResponse.postValue(handleAllOrdersResponse(response))
    }

    private fun handleAllOrdersResponse(response: Response<Orders>): Resource<Orders> {

        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun getOrderStatus(id: Int) = viewModelScope.launch {

        orderStatus.postValue(Resource.Loading())
        val response = homeRepository.getOrderStatus(id)
        orderStatus.postValue(handleOrderStatusResponse(response))
    }

    private fun handleOrderStatusResponse(response: Response<OrderResponse>): Resource<OrderResponse> {

        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}