package com.example.gosporttastingtask.ui.data.repository

import com.example.gosporttastingtask.ui.data.remote.RemoteApi
import com.example.gosporttastingtask.ui.data.remote.dto.CategoriesResponse
import com.example.gosporttastingtask.ui.data.remote.dto.ProductsResponse
import com.example.gosporttastingtask.ui.data.remote.utils.BaseApiResponse
import com.example.gosporttastingtask.ui.data.remote.utils.NetworkResult
import com.example.gosporttastingtask.ui.domain.repository.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val remoteApi: RemoteApi,
): RemoteRepository,  BaseApiResponse() {
    override suspend fun getCategories(): NetworkResult<CategoriesResponse> {
        return safeApiCall { remoteApi.getCategories() }
    }

    override suspend fun getProducts(): NetworkResult<ProductsResponse> {
        return safeApiCall { remoteApi.getProducts() }
    }

}