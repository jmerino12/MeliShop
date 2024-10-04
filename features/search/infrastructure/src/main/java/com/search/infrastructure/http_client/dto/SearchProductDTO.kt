package com.search.infrastructure.http_client.dto

import com.google.gson.annotations.SerializedName

data class SearchProductDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val name: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("condition")
    val condition: String,
    @SerializedName("original_price")
    val originalPrice: Int?,
    @SerializedName("shipping")
    val shipping: ShippingDTO?,
)