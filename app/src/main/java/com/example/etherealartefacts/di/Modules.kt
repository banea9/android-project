package com.example.etherealartefacts.di

import com.example.etherealartefacts.DefaultRepository
import com.example.etherealartefacts.networking.API
import com.example.etherealartefacts.networking.APIClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
class NetworkingModules {
    @Provides
    @Singleton
    fun providesAPI(): API {
        return APIClient().defaultService
    }
}

@Module()
@InstallIn(SingletonComponent::class)
class RepositoryModules {
    @Provides
    @Singleton
    fun providesRepository(apiService: API) = DefaultRepository(apiService)
}