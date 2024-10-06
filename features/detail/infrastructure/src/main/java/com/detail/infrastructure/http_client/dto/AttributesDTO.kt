package com.detail.infrastructure.http_client.dto

import com.google.gson.annotations.SerializedName

data class AttributesDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("value_id")
    val valueId: String?,
    @SerializedName("value_name")
    val valueName: String?
)