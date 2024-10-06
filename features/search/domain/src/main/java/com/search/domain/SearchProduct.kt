package com.search.domain

import com.core.common.domain.model.Product

class SearchProduct(
    id: String,
    name: String,
    thumbnail: String,
    price: Int,
    condition: String,
    originalPrice: Int?,
    val shipping: Shipping?,
) : Product(id, name, thumbnail, price, originalPrice, condition)

