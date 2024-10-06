package com.search.infrastructure.repository

import com.core.common.domain.exceptions.NotFoundException
import com.core.common.domain.exceptions.ServerException
import com.core.common.domain.exceptions.TechnicalException
import com.core.common.domain.exceptions.UnauthorizedException
import com.search.infrastructure.http_client.dto.SearchResponseDTO
import com.search.infrastructure.http_client.service.SearchProductService
import com.search.infrastructure.shared.MainDispatcherRule
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
class SearchProductRetrofitRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var service: SearchProductService

    @InjectMocks
    private lateinit var repository: SearchProductRetrofitRepository

    @Test(expected = com.core.common.domain.exceptions.NotFoundException::class)
    fun searchProduct_queryError_throw_404() = runTest {
        //Arrange
        val query = "test"
        val httpException = HttpException(
            Response.error<SearchResponseDTO>(
                404, Mockito.mock(
                    ResponseBody::class.java
                )
            )
        )
        Mockito.`when`(service.findProduct(query)).thenThrow(httpException)

        // Act & Assert
        repository.searchProduct(query).first()
    }

    @Test(expected = com.core.common.domain.exceptions.UnauthorizedException::class)
    fun searchProduct_throw_Unauthorized_401() = runTest {
        //Arrange
        val query = "test"
        val httpException = HttpException(
            Response.error<SearchResponseDTO>(
                401, Mockito.mock(
                    ResponseBody::class.java
                )
            )
        )
        Mockito.`when`(service.findProduct(query)).thenThrow(httpException)

        // Act & Assert
        repository.searchProduct(query).first()
    }

    @Test(expected = com.core.common.domain.exceptions.ServerException::class)
    fun searchProduct_throw_severError_500() = runTest {
        //Arrange
        val query = "test"
        val httpException = HttpException(
            Response.error<SearchResponseDTO>(
                500, Mockito.mock(
                    ResponseBody::class.java
                )
            )
        )
        Mockito.`when`(service.findProduct(query)).thenThrow(httpException)

        // Act & Assert
        repository.searchProduct(query).first()
    }

    @Test(expected = com.core.common.domain.exceptions.TechnicalException::class)
    fun searchProduct_throw_technicalException_501() = runTest {
        //Arrange
        val query = "test"
        val httpException = HttpException(
            Response.error<SearchResponseDTO>(
                501, Mockito.mock(
                    ResponseBody::class.java
                )
            )
        )
        Mockito.`when`(service.findProduct(query)).thenThrow(httpException)

        // Act & Assert
        repository.searchProduct(query).first()
    }

    @Test
    fun searchProduct_success() = runTest {
        //Arrange
        val query = "test"
        val searchDto = SearchResponseDTO(
            results = emptyList()
        )

        Mockito.`when`(service.findProduct(query)).thenReturn(searchDto)

        // Act
        val result = repository.searchProduct(query).first()

        //Assert
        Assert.assertTrue(result.isEmpty())
    }


}