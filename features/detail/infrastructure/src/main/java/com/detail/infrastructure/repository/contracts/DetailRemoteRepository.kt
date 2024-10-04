package com.detail.infrastructure.repository.contracts

import kotlinx.coroutines.flow.Flow

fun interface DetailRemoteRepository {
    fun getProductDescription(id: String): Flow<String?>
}