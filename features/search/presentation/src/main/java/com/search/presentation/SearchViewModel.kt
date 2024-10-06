package com.search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.common.domain.IoDispatcher
import com.search.infrastructure.repository.SearchProductRetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchProductRetrofitRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _searchFieldState: MutableStateFlow<SearchFieldState> =
        MutableStateFlow(SearchFieldState.Idle)
    val searchFieldState: StateFlow<SearchFieldState> = _searchFieldState

    private val _latestSearches: MutableStateFlow<List<String>> =
        MutableStateFlow(listOf())
    val latestSearches: StateFlow<List<String>> = _latestSearches

    private val _uiState = MutableStateFlow<SearchScreenUiState>(SearchScreenUiState.Initial)
    val uiState: StateFlow<SearchScreenUiState> = _uiState.asStateFlow()


    fun onQueryChange(newText: String) {
        _query.value = newText
    }

    fun searchFieldActivated() {
        activateSearchField()
    }


    fun clearInput() {
        _query.update { "" }
    }

    fun revertToInitialState() {
        _searchFieldState.update { SearchFieldState.Idle }
        val currentState = _uiState.value
        if (currentState is SearchScreenUiState.SUCCESS && currentState.items.isNotEmpty()) {
            return
        }
        _uiState.update { SearchScreenUiState.Initial }
        _query.update { "" }

    }

    private fun disableSearchField() {
        _searchFieldState.update { SearchFieldState.Idle }
    }


    private fun activateSearchField() {
        _searchFieldState.update { SearchFieldState.WithInputActive }

    }

    fun getProductsBy() {
        val query = _query.value.trim()
        if (query.isNotEmpty() && query.isNotBlank()) {
            disableSearchField()
            _uiState.update { SearchScreenUiState.LOADING }
            viewModelScope.launch(ioDispatcher) {
                try {
                    repository.searchProduct(query).collect { data ->
                        if (data.isNotEmpty()) {
                            _uiState.update { SearchScreenUiState.SUCCESS(data) }
                            addToLatestSearches(query)
                        } else {
                            _uiState.update { SearchScreenUiState.EMPTY }
                        }
                    }
                } catch (e: Exception) {
                    _uiState.update { SearchScreenUiState.ERROR(e.message.toString()) }
                    Log.e(SearchViewModel::class.simpleName, e.message.toString())
                }
            }
        }
    }

    private fun addToLatestSearches(query: String) {
        val currentList = _latestSearches.value.toMutableList()

        if (!currentList.contains(query)) {
            _latestSearches.value += query
        }

    }
}