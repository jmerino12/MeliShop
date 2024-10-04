package com.detail.infrastructure.http_client.dto

import com.google.gson.annotations.SerializedName

data class DescriptionDTO(
    @SerializedName("plain_text")
    val description: String
)
