package com.example.mendeleevapp.di

import com.example.mendeleevapp.data.PeriodicElementsRepositoryImpl
import com.example.mendeleevapp.data.nw.ApiService
import com.example.mendeleevapp.domain.PeriodicElementsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApi(): ApiService {
        return ApiService.createApiService()
    }

    @Provides
    @Singleton
    fun provideRepository(
        api: ApiService
    ): PeriodicElementsRepository {
        return PeriodicElementsRepositoryImpl(api)
    }
}