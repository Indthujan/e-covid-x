package com.app.ecovidx.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ecovidx.data.model.Category
import com.app.ecovidx.data.model.Product
import com.app.ecovidx.data.model.ProductType
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

    init {
        getCategories()
        getHomeProductList()
    }

    fun getCategories() = viewModelScope.launch {

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

    fun getHomeProductList() = viewModelScope.launch {

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
}