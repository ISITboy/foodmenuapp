package com.example.gosporttastingtask.ui.domain.repository

import com.example.gosporttastingtask.ui.data.remote.dto.CategoriesResponse
import com.example.gosporttastingtask.ui.data.remote.dto.ProductsResponse
import com.example.gosporttastingtask.ui.data.remote.utils.NetworkResult

interface RemoteRepository {

    suspend fun getCategories(): NetworkResult<CategoriesResponse>
    suspend fun getProducts(): NetworkResult<ProductsResponse>
}