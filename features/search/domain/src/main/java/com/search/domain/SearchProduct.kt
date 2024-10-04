package com.search.domain

import com.core.common.domain.model.Product

class SearchProduct(
    id: String,
    name: String,
    thumbnail: String,
    price: Int,
    condition: String,
    val shipping: Shipping?,
    val originalPrice: Int?
) : Product(id, name, thumbnail, price, condition) {

    fun calculateDiscount(): Int {
        if (originalPrice == null || originalPrice == 0 || price > originalPrice) {
            return 0
        }
        return ((originalPrice - price).toDouble() / originalPrice * 100).toInt()
    }
}

