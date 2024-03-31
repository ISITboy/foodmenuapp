package com.example.gosporttastingtask.ui.domain.mappers

import com.example.gosporttastingtask.ui.data.remote.dto.Meal
import com.example.gosporttastingtask.ui.domain.models.ModelProduct
import javax.inject.Inject

class ProductsNetworkMapper @Inject constructor() : RepositoryMapper<Meal,ModelProduct> {
    override fun mapFromEntity(entity: Meal): ModelProduct {
        return ModelProduct(
            id = entity.idMeal.toInt(),
            name = entity.strMeal,
            category = entity.strCategory,
            image = entity.strMealThumb,
            ingredients =getIngredients(entity)
        )
    }

    private fun getIngredients(entity: Meal)= listOfNotNull(
        entity.strIngredient1,
        entity.strIngredient2,
        entity.strIngredient3,
        entity.strIngredient4,
        entity.strIngredient5,
        entity.strIngredient6,
        entity.strIngredient7,
        entity.strIngredient8,
        entity.strIngredient9,
        entity.strIngredient10,
        entity.strIngredient11,
        entity.strIngredient12,
        entity.strIngredient13,
        entity.strIngredient14,
        entity.strIngredient15,
        entity.strIngredient16,
        entity.strIngredient17,
        entity.strIngredient18,
        entity.strIngredient19,
        entity.strIngredient20,
    ).filter { it.isNotBlank() }
}