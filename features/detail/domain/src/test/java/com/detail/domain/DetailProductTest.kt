package com.detail.domain

import com.detail.domain.model.Attribute
import com.detail.domain.model.DetailProduct
import com.detail.domain.model.Image
import org.junit.Assert
import org.junit.Test

class DetailProductTest {

    @Test
    fun createDetailProductObject_withDescriptionNull_success() {
        //Arrange
        val id = "2123MC"
        val name = "productName"
        val thumbnail = "thumbnail"
        val price = 0
        val condition = "used"
        val images = listOf(Image("dfsd23", "imageUrl"))
        val description: String? = null
        val originalPrice = 0
        val attributes = listOf(
            Attribute(
                "id", "name", "valueId",
                "valueName",
            )
        )

        //Act
        val result =
            DetailProduct(
                id,
                name,
                thumbnail,
                price,
                condition,
                originalPrice,
                images,
                description,
                attributes
            )

        //Assert
        Assert.assertNull(result.description)
    }

    @Test
    fun createDetailProductObject_withDescription_success() {
        //Arrange
        val id = "2123MC"
        val name = "productName"
        val thumbnail = "thumbnail"
        val price = 0
        val originalPrice = 0
        val condition = "used"
        val images = listOf(Image("dfsd23", "imageUrl"))
        val description = "descriptions"
        val attributes = listOf(
            Attribute(
                "id", "name", "valueId",
                "valueName"
            )
        )

        //Act
        val result =
            DetailProduct(
                id,
                name,
                thumbnail,
                price,
                condition,
                originalPrice,
                images,
                description,
                attributes
            )

        //Assert
        Assert.assertNotNull(result.description)
    }

    @Test
    fun createDetailProductObject_withEmptyImages_success() {
        //Arrange
        val id = "2123MC"
        val name = "productName"
        val thumbnail = "thumbnail"
        val price = 0
        val originalPrice = 0
        val condition = "used"
        val images = listOf<Image>()
        val description: String? = null
        val attributes = listOf(
            Attribute(
                "id", "name", "valueId",
                "valueName"
            )
        )

        //Act
        val result =
            DetailProduct(
                id,
                name,
                thumbnail,
                price,
                condition,
                originalPrice,
                images,
                description,
                attributes
            )

        //Assert
        Assert.assertTrue(result.images.isEmpty())
    }

    @Test
    fun createDetailProductObject_withEmptyAttributes_success() {
        //Arrange
        val id = "2123MC"
        val name = "productName"
        val thumbnail = "thumbnail"
        val price = 0
        val originalPrice = 0
        val condition = "used"
        val images = listOf(Image("dfsd23", "imageUrl"))
        val description: String? = null
        val attributes = listOf<Attribute>()

        //Act
        val result =
            DetailProduct(
                id,
                name,
                thumbnail,
                price,
                condition,
                originalPrice,
                images,
                description,
                attributes
            )

        //Assert
        Assert.assertTrue(result.attributes.isEmpty())
    }
}