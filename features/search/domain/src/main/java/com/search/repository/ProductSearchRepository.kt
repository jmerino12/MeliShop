package com.search.repository

import com.search.domain.SearchProduct
import kotlinx.coroutines.flow.Flow

fun interface ProductSearchRepository {
    fun searchProduct(productName: String): Flow<List<SearchProduct>>
}