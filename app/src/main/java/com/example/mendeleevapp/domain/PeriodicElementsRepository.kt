package com.example.mendeleevapp.domain

import com.example.mendeleevapp.domain.model.PeriodicTable

interface PeriodicElementsRepository {
    suspend fun getTable(): PeriodicTable
}