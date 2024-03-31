package com.example.gosporttastingtask.ui.domain.mappers

interface RepositoryMapper<NetworkEntity, DomainModel> {
    fun mapFromEntity(entity: NetworkEntity):DomainModel
}