package com.detail.infrastructure.http_client.service

import com.detail.infrastructure.http_client.dto.DescriptionDTO
import com.detail.infrastructure.http_client.dto.DetailProductDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailProductService {

    @GET("/items/{id}")
    suspend fun getProductDetail(@Path("id") id: String): DetailProductDTO

    @GET("items/{id}/description")
    suspend fun getProductDescription(@Path("id") id: String): DescriptionDTO
}