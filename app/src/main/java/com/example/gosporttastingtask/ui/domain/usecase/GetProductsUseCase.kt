package com.example.gosporttastingtask.ui.domain.usecase

import com.example.gosporttastingtask.ui.domain.repository.RemoteRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
){
    suspend operator fun invoke() = remoteRepository.getProducts()
}