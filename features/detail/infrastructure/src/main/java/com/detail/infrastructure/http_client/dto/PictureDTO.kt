package com.detail.infrastructure.http_client.dto

import com.google.gson.annotations.SerializedName

data class PictureDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("secure_url")
    val url: String
)
