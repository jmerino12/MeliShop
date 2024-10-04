package com.search.infrastructure.http_client.dto

import com.google.gson.annotations.SerializedName

data class SearchResponseDTO(
    @SerializedName("results") val results: List<SearchProductDTO>
)