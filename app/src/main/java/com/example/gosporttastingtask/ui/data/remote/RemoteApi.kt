package com.example.gosporttastingtask.ui.data.remote
import com.example.gosporttastingtask.ui.data.remote.dto.CategoriesResponse
import com.example.gosporttastingtask.ui.data.remote.dto.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET


interface RemoteApi {

    @GET("categories.php")
    suspend fun getCategories():Response<CategoriesResponse>

    @GET("search.php?s")
    suspend fun getProducts():Response<ProductsResponse>
}