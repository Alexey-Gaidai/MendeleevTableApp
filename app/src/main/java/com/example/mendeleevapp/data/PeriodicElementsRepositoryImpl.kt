package com.example.mendeleevapp.data

import com.example.mendeleevapp.data.nw.ApiService
import com.example.mendeleevapp.domain.PeriodicElementsRepository
import com.example.mendeleevapp.domain.model.PeriodicTable

class PeriodicElementsRepositoryImpl(private val api: ApiService) : PeriodicElementsRepository {
    override suspend fun getTable(): PeriodicTable {
        return try {
            val response = api.getTableElements()
            if (response.isSuccessful && response.body() != null)
                response.body()!!
            else
                PeriodicTable(emptyList())
        } catch (e: Throwable) {
            PeriodicTable(emptyList())
        }
    }
}