package com.search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.common.domain.IoDispatcher
import com.search.infrastructure.repository.SearchProductRetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchProductRetrofitRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchScreenUiState>(SearchScreenUiState.LOADING)
    val uiState: StateFlow<SearchScreenUiState> = _uiState.onStart {
        getProductsBy()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        initialValue = SearchScreenUiState.LOADING
    )

    private fun getProductsBy(query: String = "Macbook Pro 16") {
        viewModelScope.launch(ioDispatcher) {
            try {
                repository.searchProduct(query).collect { data ->
                    _uiState.update { SearchScreenUiState.SUCCESS(data) }
                }
            } catch (e: Exception) {
                _uiState.update { SearchScreenUiState.ERROR(e.message.toString()) }
                Log.e(SearchViewModel::class.simpleName, e.message.toString())
            }
        }
    }

}