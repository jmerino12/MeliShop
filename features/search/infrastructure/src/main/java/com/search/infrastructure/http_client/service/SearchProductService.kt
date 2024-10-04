package com.search.infrastructure.http_client.service

import com.search.infrastructure.http_client.dto.SearchResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

fun interface SearchProductService {
    @GET("sites/MCO/search")
    suspend fun findProduct(@Query("q") query: String): SearchResponseDTO
}