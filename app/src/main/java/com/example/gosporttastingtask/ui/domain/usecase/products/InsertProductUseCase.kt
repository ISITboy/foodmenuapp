package com.example.gosporttastingtask.ui.domain.usecase.products

import com.example.gosporttastingtask.ui.domain.models.ModelProduct
import com.example.gosporttastingtask.ui.domain.repository.ProductsRepository
import javax.inject.Inject

class InsertProductUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
){
    suspend operator fun invoke(product:ModelProduct) = productsRepository.insert(product=product)

}