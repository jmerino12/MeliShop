package com.detail.infrastructure.shared.dependency_injection

import com.detail.domain.repository.DetailProductRepository
import com.detail.infrastructure.http_client.service.DetailProductService
import com.detail.infrastructure.repository.DetailProductRetrofitRepository
import com.detail.infrastructure.repository.contracts.DetailRemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DetailInfrastructureModule {
    @Provides
    @Singleton
    fun providesDetailProductService(
        retrofit: Retrofit
    ): DetailProductService = retrofit.create(DetailProductService::class.java)

    @Provides
    @Singleton
    fun providesDetailProductRetrofitRepository(
        detailProductService: DetailProductService
    ): DetailProductRetrofitRepository = DetailProductRetrofitRepository(detailProductService)

    @Provides
    @Singleton
    fun providesDetailProductRepository(
        repository: DetailProductRetrofitRepository
    ): DetailProductRepository = repository

    @Provides
    @Singleton
    fun providesDetailRemoteRepository(
        repository: DetailProductRetrofitRepository
    ): DetailRemoteRepository = repository
}