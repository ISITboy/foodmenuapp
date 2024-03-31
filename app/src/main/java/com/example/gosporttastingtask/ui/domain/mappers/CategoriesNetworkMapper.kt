package com.example.gosporttastingtask.ui.domain.mappers

import com.example.gosporttastingtask.ui.data.remote.dto.Category
import com.example.gosporttastingtask.ui.domain.models.ModelCategory
import javax.inject.Inject

class CategoriesNetworkMapper @Inject constructor() : RepositoryMapper<Category, ModelCategory> {
    override fun mapFromEntity(entity: Category): ModelCategory {
        return ModelCategory(id = entity.idCategory, name = entity.strCategory)
    }
}