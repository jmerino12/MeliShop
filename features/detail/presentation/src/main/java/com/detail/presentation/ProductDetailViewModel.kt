package com.detail.presentation

import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.common.domain.IoDispatcher
import com.core.common.domain.exceptions.ServerException
import com.core.common.domain.exceptions.UnauthorizedException
import com.detail.domain.model.DetailProduct
import com.detail.domain.repository.DetailProductRepository
import com.detail.infrastructure.repository.contracts.DetailRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailRemoteRepository: DetailRemoteRepository,
    private val detailProductRepository: DetailProductRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val productId = checkNotNull(savedStateHandle.get<String>("productId"))

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.message?.let { Log.e("Error", it) }
    }

    private val _uiState = MutableStateFlow<DetailScreenUiState>(DetailScreenUiState.Initial)
    val uiState: StateFlow<DetailScreenUiState> = _uiState.onStart {
        getDetailProduct()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        initialValue = DetailScreenUiState.Initial
    )

    private var job: Job? = null

    fun onRetry() {
        getDetailProduct()
    }

    private fun getDetailProduct() {
        _uiState.update { DetailScreenUiState.LOADING }

        job?.cancel()

        job = viewModelScope.launch(ioDispatcher + exceptionHandler) {
            try {
                val productDetailDeferred =
                    async { detailProductRepository.getProductDetail(productId).first() }
                val productDescriptionDeferred =
                    async { detailRemoteRepository.getProductDescription(productId).first() }

                val productDetail = productDetailDeferred.await()
                val productDescription = productDescriptionDeferred.await()

                if (productDetail != null && productDescription != null) {
                    val updatedProductDetail = updateProductDetailWithDescription(
                        productDetail = productDetail,
                        description = productDescription
                    )
                    _uiState.update {
                        DetailScreenUiState.SUCCESS(updatedProductDetail)
                    }
                }

            } catch (e: Exception) {
                val error: DetailScreenError = when (e) {
                    is NotFoundException -> DetailScreenError.NotFound
                    is ServerException -> DetailScreenError.ServerError
                    else -> DetailScreenError.TechnicalError(e.message)
                }
                _uiState.update { DetailScreenUiState.ERROR(error) }
            }
        }
    }

    private fun updateProductDetailWithDescription(
        productDetail: DetailProduct,
        description: String?
    ): DetailProduct {
        return DetailProduct(
            id = productDetail.id,
            name = productDetail.name,
            thumbnail = productDetail.thumbnail,
            price = productDetail.price,
            condition = productDetail.condition,
            images = productDetail.images,
            description = description,
            attributes = productDetail.attributes,
            originalPrice = productDetail.originalPrice
        )
    }

}