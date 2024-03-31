package com.example.gosporttastingtask.ui.domain.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Status>

    enum class Status{
        Available,Unavailable,Losing,Lost
    }
}