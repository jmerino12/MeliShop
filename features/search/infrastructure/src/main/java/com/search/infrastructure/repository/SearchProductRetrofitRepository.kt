package com.search.infrastructure.repository

import com.core.common.domain.exceptions.ExceptionTranslate
import com.search.domain.SearchProduct
import com.search.infrastructure.anticorruption.SearchProductTranslate
import com.search.infrastructure.http_client.service.SearchProductService
import com.search.repository.ProductSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchProductRetrofitRepository @Inject constructor(private val searchProductService: SearchProductService) :
    ProductSearchRepository {

    override fun searchProduct(productName: String): Flow<List<SearchProduct>> {
        return flow {
            val searchProductsResult = searchProductService.findProduct(productName).results.map {
                SearchProductTranslate.fromSearchProductDtoToDomain(it)
            }
            emit(searchProductsResult)
        }.catch {
            throw ExceptionTranslate.mapToDomainException(it)
        }
    }
}