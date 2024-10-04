package com.detail.infrastructure

import com.detail.infrastructure.http_client.dto.DetailProductDTO
import com.detail.infrastructure.http_client.service.DetailProductService
import com.detail.infrastructure.repository.DetailProductRetrofitRepository
import com.detail.shared.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailProductRetrofitRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var service: DetailProductService

    @InjectMocks
    private lateinit var repository: DetailProductRetrofitRepository

    private val productDetailDto = DetailProductDTO(
        id = "",
        name = "",
        thumbnail = "",
        price = 0.0,
        condition = "",
        images = listOf(),
        attributes = listOf()
    )


    @Test
    fun getProductDetail_success() = runTest {
        //Arrange
        val id = "MC1234"

        Mockito.`when`(service.getProductDetail(id)).thenReturn(productDetailDto)
        //Act
        val result = repository.getProductDetail(id).first()

        //Arrange
        Assert.assertNotNull(result)
        Assert.assertEquals(result!!.id, productDetailDto.id)

    }
}