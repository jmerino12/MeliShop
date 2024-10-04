package com.search.infrastructure.http_client.dto

import com.google.gson.annotations.SerializedName

data class ShippingDTO(
    @SerializedName("free_shipping")
    val freeShipping: Boolean,
)