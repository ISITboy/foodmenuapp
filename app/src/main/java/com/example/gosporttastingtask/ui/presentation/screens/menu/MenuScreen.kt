package com.example.gosporttastingtask.ui.presentation.screens.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gosporttastingtask.R
import com.example.gosporttastingtask.ui.data.remote.dto.CategoriesResponse
import com.example.gosporttastingtask.ui.data.remote.dto.ProductsResponse
import com.example.gosporttastingtask.ui.data.remote.utils.NetworkResult
import com.example.gosporttastingtask.ui.domain.models.ModelCategory
import com.example.gosporttastingtask.ui.domain.models.ModelProduct
import com.example.gosporttastingtask.ui.presentation.screens.menu.components.BannerLayout
import com.example.gosporttastingtask.ui.presentation.screens.menu.components.CategoriesLayout
import com.example.gosporttastingtask.ui.presentation.screens.menu.components.CircularLoadingScreen
import com.example.gosporttastingtask.ui.presentation.screens.menu.components.ErrorScreen
import com.example.gosporttastingtask.ui.presentation.screens.menu.components.LinearLoadingScreen
import com.example.gosporttastingtask.ui.presentation.screens.menu.components.ProductCardsLayout

@Composable
fun MenuScreen(
    viewModel: MenuScreenViewModel,
    categoriesState: NetworkResult<CategoriesResponse>,
    productsState: NetworkResult<ProductsResponse>,
) {
    val lazyListState = rememberLazyListState()
    val selectedCategory = remember { mutableStateOf<ModelCategory?>(null) }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        BannerLayout(listOf(R.drawable.banner_img1, R.drawable.banner_img2), lazyListState = lazyListState)
        Spacer(modifier = Modifier.height(20.dp))
        when (categoriesState) {
            is NetworkResult.Success -> {
                val categories =
                    viewModel.mappingCategories(categoriesState.data?.categories ?: listOf())
                CategoriesLayout(categories,selectedCategory)
            }
            is NetworkResult.Error -> {
                ErrorScreen(
                    categoriesState.message ?: "Oh, some error!",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                )
            }
            is NetworkResult.Loading -> {
                LinearLoadingScreen()
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        when (productsState) {
            is NetworkResult.Success -> {
                val products = viewModel.mappingProducts(productsState.data?.meals ?: listOf())
                ProductCardsLayout(
                    viewModel.getProductsByCategory(products,selectedCategory.value),
                    lazyListState = lazyListState)
            }
            is NetworkResult.Error -> {
                ErrorScreen(categoriesState.message ?: "Oh, some error!")
            }
            is NetworkResult.Loading -> {
                CircularLoadingScreen()
            }
        }
    }
}


@Composable
fun MenuScreen(
    viewModel: MenuScreenViewModel,
    products:List<ModelProduct>,
    categories: List<String>
) {
    val lazyListState = rememberLazyListState()
    val selectedCategory = remember { mutableStateOf<ModelCategory?>(null) }
    selectedCategory.value?.let { viewModel.getLocalProducts(it.name) }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        BannerLayout(listOf(R.drawable.banner_img1, R.drawable.banner_img2), lazyListState = lazyListState)
        Spacer(modifier = Modifier.height(20.dp))
        CategoriesLayout(categories.map { ModelCategory(name = it) },selectedCategory)
        Spacer(modifier = Modifier.height(20.dp))
        ProductCardsLayout(products, lazyListState = lazyListState)
    }
}


val LazyListState.isScrolled : Boolean
    get() = firstVisibleItemIndex>0
