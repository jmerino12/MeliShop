package com.detail.infrastructure.anticorruption

import com.detail.domain.model.Image
import com.detail.infrastructure.http_client.dto.PictureDTO

class ImageTranslate {

    companion object {
        fun fromImageDtoToDomain(pictureDTO: PictureDTO): Image {
            return Image(pictureDTO.id, pictureDTO.url)
        }
    }

}