package com.detail.infrastructure.anticorruption

import com.detail.domain.model.Attribute
import com.detail.infrastructure.http_client.dto.AttributesDTO

class AttributeTranslate {

    companion object {

        fun fromAttributeDtoToDomain(attributesDTO: AttributesDTO): Attribute {
            return Attribute(
                attributesDTO.id,
                attributesDTO.name,
                attributesDTO.valueId,
                attributesDTO.valueName
            )
        }
    }

}