package com.example.gosporttastingtask.ui.data.repository

import com.example.gosporttastingtask.ui.data.local.ProductsDao
import com.example.gosporttastingtask.ui.domain.models.ModelProduct
import com.example.gosporttastingtask.ui.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productsDao: ProductsDao
) : ProductsRepository {
    override fun getAllProducts(): Flow<List<ModelProduct>> = productsDao.getAllProducts()

    override fun getCategories(): Flow<List<String>> = productsDao.getCategories()

    override suspend fun insert(product: ModelProduct) {
        productsDao.insert(product = product)
    }

    override fun getProductsByCategory(category: String): Flow<List<ModelProduct>> =
        productsDao.getProductsByCategory(category = category)
}