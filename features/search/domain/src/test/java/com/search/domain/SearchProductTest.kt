package com.search.domain

import org.junit.Assert
import org.junit.Test


class SearchProductTest {
    @Test
    fun createSearchProduct_success() {
        //Arrange
        val id = "2123MC"
        val name = "productName"
        val thumbnail = "thumbnail"
        val price = 0
        val condition = "new"
        val shipping = Shipping(isFreeShipping = false)
        val originalPrice: Int? = null


        //Act
        val result =
            SearchProduct(id, name, thumbnail, price, condition, originalPrice, shipping)

        //Assert
        Assert.assertNotNull(result)
    }

    @Test
    fun createSearchProduct_withOfferPrice_discountZeroPercent_success() {
        //Arrange
        val id = "2123MC"
        val name = "productName"
        val thumbnail = "thumbnail"
        val price = 0
        val condition = "new"
        val shipping = Shipping(isFreeShipping = false)
        val originalPrice = 0


        val product =
            SearchProduct(id, name, thumbnail, price, condition, originalPrice,shipping)

        //Act
        val discount = product.calculateDiscount()


        //Assert
        Assert.assertTrue(discount == 0)
    }

    @Test
    fun createSearchProduct_withOfferPrice_discountDifferentToZeroPercent_success() {
        //Arrange
        val id = "2123MC"
        val name = "productName"
        val thumbnail = "thumbnail"
        val price = 14325654
        val condition = "new"
        val shipping = Shipping(isFreeShipping = false)
        val originalPrice = 19325654


        val product =
            SearchProduct(id, name, thumbnail, price, condition, originalPrice,shipping)

        //Act
        val discount = product.calculateDiscount()


        //Assert
        Assert.assertTrue(discount == 25)
    }

    @Test
    fun createSearchProduct_withOriginalPriceIsMoreExpensiveThanPrice_discountEqual0_success() {
        //Arrange
        val id = "2123MC"
        val name = "productName"
        val thumbnail = "thumbnail"
        val price = 19325654
        val condition = "new"
        val shipping = Shipping(isFreeShipping = false)
        val originalPrice = 14325654


        val product =
            SearchProduct(id, name, thumbnail, price, condition, originalPrice,shipping)

        //Act
        val discount = product.calculateDiscount()


        //Assert
        Assert.assertTrue(discount == 0)
    }

    @Test
    fun createSearchProduct_shippingIsNotFree_success() {
        //Arrange
        val id = "2123MC"
        val name = "productName"
        val thumbnail = "thumbnail"
        val price = 14325654
        val condition = "new"
        val shipping = Shipping(isFreeShipping = false)
        val originalPrice = 19325654


        //Act
        val product =
            SearchProduct(id, name, thumbnail, price, condition, originalPrice,shipping)


        //Assert
        product.shipping?.let { Assert.assertFalse(it.isFreeShipping) }
    }


    @Test
    fun createSearchProduct_shippingIsFree_success() {
        //Arrange
        val id = "2123MC"
        val name = "productName"
        val thumbnail = "thumbnail"
        val price = 14325654
        val condition = "new"
        val shipping = Shipping(isFreeShipping = true)
        val originalPrice = 19325654


        //Act
        val product =
            SearchProduct(id, name, thumbnail, price, condition, originalPrice,shipping)


        //Assert
        product.shipping?.let { Assert.assertTrue(it.isFreeShipping) }
    }
}