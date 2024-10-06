package com.detail.infrastructure.repository


import com.core.common.domain.exceptions.ExceptionTranslate
import com.detail.domain.model.DetailProduct
import com.detail.domain.repository.DetailProductRepository
import com.detail.infrastructure.anticorruption.DetailProductTranslate
import com.detail.infrastructure.http_client.service.DetailProductService
import com.detail.infrastructure.repository.contracts.DetailRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailProductRetrofitRepository @Inject constructor(private val productDetailService: DetailProductService) :
    DetailRemoteRepository, DetailProductRepository {

    override fun getProductDetail(id: String): Flow<DetailProduct?> {
        return flow {
            val productDetail = productDetailService.getProductDetail(id)
            emit(DetailProductTranslate.fromProductDetailDtoToDomain(productDetail))
        }.catch {
            throw ExceptionTranslate.mapToDomainException(it)
        }
    }

    override fun getProductDescription(id: String): Flow<String?> {
        return flow {
            emit(productDetailService.getProductDescription(id).description)
        }.catch {
            throw ExceptionTranslate.mapToDomainException(it)
        }
    }
}