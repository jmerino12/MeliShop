package com.search.infrastructure.anticorruption

import com.search.domain.SearchProduct
import com.search.domain.Shipping
import com.search.infrastructure.http_client.dto.SearchProductDTO
import com.search.infrastructure.http_client.dto.ShippingDTO

class SearchProductTranslate {

    companion object {
        fun fromSearchProductDtoToDomain(searchProductDTO: SearchProductDTO): SearchProduct {
            return SearchProduct(
                id = searchProductDTO.id,
                name = searchProductDTO.name,
                condition = searchProductDTO.condition,
                thumbnail = searchProductDTO.thumbnail,
                price = searchProductDTO.price,
                originalPrice = searchProductDTO.originalPrice,
                shipping = searchProductDTO.shipping?.let { shipping ->
                    fromShippingDtoToDomain(shipping)
                }
            )
        }

        private fun fromShippingDtoToDomain(shippingDTO: ShippingDTO): Shipping {
            return Shipping(shippingDTO.freeShipping)
        }
    }

}