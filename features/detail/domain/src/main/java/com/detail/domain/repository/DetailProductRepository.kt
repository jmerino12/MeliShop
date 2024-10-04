package com.detail.domain.repository

import com.detail.domain.model.DetailProduct
import kotlinx.coroutines.flow.Flow

fun interface DetailProductRepository {
    fun getProductDetail(id: String): Flow<DetailProduct?>
}