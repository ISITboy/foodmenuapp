package com.example.gosporttastingtask.ui.presentation.screens.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gosporttastingtask.ui.data.remote.dto.CategoriesResponse
import com.example.gosporttastingtask.ui.data.remote.dto.Category
import com.example.gosporttastingtask.ui.data.remote.dto.Meal
import com.example.gosporttastingtask.ui.data.remote.dto.ProductsResponse
import com.example.gosporttastingtask.ui.data.remote.utils.NetworkResult
import com.example.gosporttastingtask.ui.domain.mappers.CategoriesNetworkMapper
import com.example.gosporttastingtask.ui.domain.mappers.ProductsNetworkMapper
import com.example.gosporttastingtask.ui.domain.models.ModelCategory
import com.example.gosporttastingtask.ui.domain.models.ModelProduct
import com.example.gosporttastingtask.ui.domain.usecase.GetCategoriesUseCase
import com.example.gosporttastingtask.ui.domain.usecase.GetProductsUseCase
import com.example.gosporttastingtask.ui.domain.usecase.products.GetLocalCategoriesUseCase
import com.example.gosporttastingtask.ui.domain.usecase.products.GetProductsByCategoryUseCase
import com.example.gosporttastingtask.ui.domain.usecase.products.InsertProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuScreenViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val categoriesNetworkMapper: CategoriesNetworkMapper,
    private val productsNetworkMapper: ProductsNetworkMapper,
    private val insertProductUseCase: InsertProductUseCase,
    private val getLocalCategoriesUseCase: GetLocalCategoriesUseCase,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase
) : ViewModel() {

    init {
        getLocalProducts()
        getLocalCategories()
    }

    private var resultProductsList = listOf<ModelProduct>()


    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>>
        get() = _categories

    fun getLocalCategories() {
        viewModelScope.launch {
            getLocalCategoriesUseCase().collect {
                _categories.postValue(it.distinct())
            }
        }
    }

    private val _products = MutableLiveData<List<ModelProduct>>()
    val products: LiveData<List<ModelProduct>>
        get() = _products

    fun getLocalProducts(category: String = "%") {
        viewModelScope.launch {
            getProductsByCategoryUseCase(category).collect {
                _products.postValue(it)
            }
        }
    }

    private val _categoriesApi = MutableLiveData<NetworkResult<CategoriesResponse>>()
    val categoriesApi: LiveData<NetworkResult<CategoriesResponse>>
        get() = _categoriesApi

    fun getAllCategories() {
        viewModelScope.launch {
            getCategoriesUseCase().let {
                _categoriesApi.value = it
            }
        }
    }

    private val _productsApi = MutableLiveData<NetworkResult<ProductsResponse>>()
    val productsApi: LiveData<NetworkResult<ProductsResponse>>
        get() = _productsApi

    fun getAllProducts() {
        viewModelScope.launch {
            getProductsUseCase().let {
                _productsApi.value = it
            }
        }
    }

    fun mappingCategories(entities: List<Category>) =
        entities.map { entity -> categoriesNetworkMapper.mapFromEntity(entity) }

    fun mappingProducts(entities: List<Meal>): List<ModelProduct> {
        resultProductsList = entities.map { entity -> productsNetworkMapper.mapFromEntity(entity) }
        return resultProductsList
    }

    fun saveDataInDatabase() = viewModelScope.launch {
        resultProductsList.forEach {
            insertProductUseCase(it)
        }
    }

    fun getProductsByCategory(
        products: List<ModelProduct>,
        modelCategory: ModelCategory?
    ): List<ModelProduct> {
        return if (modelCategory == null) products
        else if (modelCategory.name == "%") products
        else products.filter { it.category == modelCategory.name }
    }

}