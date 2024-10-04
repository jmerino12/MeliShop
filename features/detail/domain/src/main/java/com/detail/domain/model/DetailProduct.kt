package com.detail.domain.model

import com.core.common.domain.model.Product

class DetailProduct(
    id: String,
    name: String,
    thumbnail: String,
    price: Int,
    condition: String,
    val images: List<Image>,
    val description: String?,
    val attributes: List<Attribute>
) : Product(id, name, thumbnail, price, condition)


