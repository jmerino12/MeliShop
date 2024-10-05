package com.search.infrastructure.shared.dependency_injection

import com.search.infrastructure.http_client.service.SearchProductService
import com.search.infrastructure.repository.SearchProductRetrofitRepository
import com.search.repository.ProductSearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchInfrastructureModule {
    @Provides
    @Singleton
    fun providesSearchProductService(
        retrofit: Retrofit
    ): SearchProductService = retrofit.create(SearchProductService::class.java)

    @Provides
    @Singleton
    fun providesSearchProductRemoteRepository(
        searchProductService: SearchProductService
    ): ProductSearchRepository = SearchProductRetrofitRepository(searchProductService)
}