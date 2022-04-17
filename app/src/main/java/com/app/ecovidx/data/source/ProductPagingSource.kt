package com.app.ecovidx.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.ecovidx.api.APIService
import com.app.ecovidx.data.model.Product
import retrofit2.HttpException
import java.io.IOException

const val NETWORK_PAGE_SIZE = 20
private const val INITIAL_LOAD_SIZE = 1

class ProductPagingSource(
    private val apiService: APIService,
    private val id: Int
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val position = params.key ?: INITIAL_LOAD_SIZE
        val offset = if (params.key != null) ((position - 1) * NETWORK_PAGE_SIZE) + 1 else INITIAL_LOAD_SIZE

        return try {
            val response = apiService.getProductsByCategoryID(id, offset, params.loadSize)
            val nextKey = if (response.body()?.isEmpty()!!) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = response.body()!!,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return null
    }
}