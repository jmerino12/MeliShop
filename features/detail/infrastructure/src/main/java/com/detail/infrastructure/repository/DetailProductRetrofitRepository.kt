package com.detail.infrastructure.repository


import com.detail.domain.model.DetailProduct
import com.detail.domain.repository.DetailProductRepository
import com.detail.infrastructure.anticorruption.DetailProductTranslate
import com.detail.infrastructure.http_client.service.DetailProductService
import com.detail.infrastructure.repository.contracts.DetailRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailProductRetrofitRepository(private val productDetailService: DetailProductService) :
    DetailRemoteRepository, DetailProductRepository {

    override fun getProductDetail(id: String): Flow<DetailProduct?> {
        return flow {
            val productDetail = productDetailService.getProductDetail(id)
            emit(DetailProductTranslate.fromProductDetailDtoToDomain(productDetail))
        }
    }

    override fun getProductDescription(id: String): Flow<String?> {
        return flow {
            emit(productDetailService.getProductDescription(id).description)
        }
    }

}