package com.detail.infrastructure.anticorruption

import com.detail.domain.model.DetailProduct
import com.detail.infrastructure.http_client.dto.DetailProductDTO

class DetailProductTranslate {
    companion object {
        fun fromProductDetailDtoToDomain(productDTO: DetailProductDTO): DetailProduct {
            return with(productDTO) {
                DetailProduct(
                    id,
                    name,
                    thumbnail,
                    price,
                    condition,
                    productDTO.images.map { imageDto -> ImageTranslate.fromImageDtoToDomain(imageDto) },
                    description = null,
                    productDTO.attributes.map { attributeDTO ->
                        AttributeTranslate.fromAttributeDtoToDomain(
                            attributeDTO
                        )
                    }
                )
            }
        }
    }
}