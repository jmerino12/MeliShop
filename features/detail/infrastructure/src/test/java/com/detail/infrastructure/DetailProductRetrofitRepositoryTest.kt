package com.detail.infrastructure

import com.core.common.domain.exceptions.NotFoundException
import com.core.common.domain.exceptions.ServerException
import com.core.common.domain.exceptions.TechnicalException
import com.core.common.domain.exceptions.UnauthorizedException
import com.detail.infrastructure.http_client.dto.DetailProductDTO
import com.detail.infrastructure.http_client.service.DetailProductService
import com.detail.infrastructure.repository.DetailProductRetrofitRepository
import com.detail.shared.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

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
        price = 0,
        condition = "",
        images = listOf(),
        attributes = listOf(),
        originalPrice = null
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

    @Test(expected = NotFoundException::class)
    fun getProductDetail_queryError_throw_404() = runTest {
        //Arrange
        val query = "test"
        val httpException = HttpException(
            Response.error<DetailProductDTO>(
                404, Mockito.mock(
                    ResponseBody::class.java
                )
            )
        )
        Mockito.`when`(service.getProductDetail(query)).thenThrow(httpException)

        // Act & Assert
        repository.getProductDetail(query).first()
    }

    @Test(expected = NotFoundException::class)
    fun getProductDescription_queryError_throw_404() = runTest {
        //Arrange
        val query = "test"
        val httpException = HttpException(
            Response.error<DetailProductDTO>(
                404, Mockito.mock(
                    ResponseBody::class.java
                )
            )
        )
        Mockito.`when`(service.getProductDescription(query)).thenThrow(httpException)

        // Act & Assert
        repository.getProductDescription(query).first()
    }

    @Test(expected = UnauthorizedException::class)
    fun getProductDetail_throw_Unauthorized_401() = runTest {
        //Arrange
        val query = "test"
        val httpException = HttpException(
            Response.error<DetailProductDTO>(
                401, Mockito.mock(
                    ResponseBody::class.java
                )
            )
        )
        Mockito.`when`(service.getProductDetail(query)).thenThrow(httpException)

        // Act & Assert
        repository.getProductDetail(query).first()
    }

    @Test(expected = UnauthorizedException::class)
    fun getProductDescription_throw_Unauthorized_401() = runTest {
        //Arrange
        val query = "test"
        val httpException = HttpException(
            Response.error<DetailProductDTO>(
                401, Mockito.mock(
                    ResponseBody::class.java
                )
            )
        )
        Mockito.`when`(service.getProductDescription(query)).thenThrow(httpException)

        // Act & Assert
        repository.getProductDescription(query).first()
    }

    @Test(expected = ServerException::class)
    fun getProductDescription_throw_severError_500() = runTest {
        //Arrange
        val query = "test"
        val httpException = HttpException(
            Response.error<DetailProductDTO>(
                500, Mockito.mock(
                    ResponseBody::class.java
                )
            )
        )
        Mockito.`when`(service.getProductDescription(query)).thenThrow(httpException)

        // Act & Assert
        repository.getProductDescription(query).first()
    }

    @Test(expected = ServerException::class)
    fun getProductDetail_throw_severError_500() = runTest {
        //Arrange
        val query = "test"
        val httpException = HttpException(
            Response.error<DetailProductDTO>(
                500, Mockito.mock(
                    ResponseBody::class.java
                )
            )
        )
        Mockito.`when`(service.getProductDetail(query)).thenThrow(httpException)

        // Act & Assert
        repository.getProductDetail(query).first()
    }


    @Test(expected = TechnicalException::class)
    fun getProductDetail_throw_technicalException_501() = runTest {
        //Arrange
        val query = "test"
        val httpException = HttpException(
            Response.error<DetailProductDTO>(
                501, Mockito.mock(
                    ResponseBody::class.java
                )
            )
        )
        Mockito.`when`(service.getProductDetail(query)).thenThrow(httpException)

        // Act & Assert
        repository.getProductDetail(query).first()
    }

    @Test(expected = TechnicalException::class)
    fun getProductDescriptionl_throw_technicalException_501() = runTest {
        //Arrange
        val query = "test"
        val httpException = HttpException(
            Response.error<DetailProductDTO>(
                501, Mockito.mock(
                    ResponseBody::class.java
                )
            )
        )
        Mockito.`when`(service.getProductDescription(query)).thenThrow(httpException)

        // Act & Assert
        repository.getProductDescription(query).first()
    }
}