package com.search.presentation

sealed class SearchFieldState {
    data object Idle : SearchFieldState()
    data object WithInputActive : SearchFieldState()
}