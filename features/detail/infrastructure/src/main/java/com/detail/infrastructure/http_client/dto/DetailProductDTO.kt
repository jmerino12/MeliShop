package com.detail.infrastructure.http_client.dto

import com.google.gson.annotations.SerializedName

data class DetailProductDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val name: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("condition")
    val condition: String,
    @SerializedName("pictures")
    val images: List<PictureDTO>,
    @SerializedName("attributes")
    val attributes: List<AttributesDTO>
)


