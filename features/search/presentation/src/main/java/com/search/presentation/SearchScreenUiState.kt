package com.search.presentation

import com.search.domain.SearchProduct

sealed class SearchScreenUiState {
    data object LOADING : SearchScreenUiState()
    data class SUCCESS(val items: List<SearchProduct>) : SearchScreenUiState()
    data object EMPTY : SearchScreenUiState()
    data class ERROR(val message: String?) : SearchScreenUiState()
}