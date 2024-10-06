package com.detail.presentation

import com.detail.domain.model.DetailProduct

sealed class DetailScreenUiState {
    data object Initial : DetailScreenUiState()
    data object LOADING : DetailScreenUiState()
    data class SUCCESS(val product: DetailProduct) : DetailScreenUiState()
    data class ERROR(val error: DetailScreenError) : DetailScreenUiState()
}

sealed class DetailScreenError {
    data object NotFound : DetailScreenError()
    data object ServerError : DetailScreenError()
    data class TechnicalError(val message: String?) : DetailScreenError()
}