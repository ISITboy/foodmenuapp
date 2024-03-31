package com.example.gosporttastingtask.ui.domain.usecase.products

import com.example.gosporttastingtask.ui.domain.repository.ProductsRepository
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
){
    operator fun invoke(category:String) = productsRepository.getProductsByCategory(category=category)

}