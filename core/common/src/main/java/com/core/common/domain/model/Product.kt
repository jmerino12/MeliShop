package com.core.common.domain.model

open class Product(
    val id: String,
    val name: String,
    val thumbnail: String,
    val price: Int,
    val originalPrice: Int?,
    val condition: String
) {
    fun calculateDiscount(): Int {
        if (originalPrice == null || originalPrice == 0 || price > originalPrice) {
            return 0
        }
        return ((originalPrice - price).toDouble() / originalPrice * 100).toInt()
    }
}