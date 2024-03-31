package com.example.gosporttastingtask.ui.domain.repository

import com.example.gosporttastingtask.ui.domain.models.ModelProduct
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    fun getAllProducts(): Flow<List<ModelProduct>>


    fun getCategories(): Flow<List<String>>


    suspend fun insert(product: ModelProduct)


    fun getProductsByCategory(category:String): Flow<List<ModelProduct>>
}