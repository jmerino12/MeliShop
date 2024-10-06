package com.search.presentation

import android.util.Log
import com.search.domain.SearchProduct
import com.search.infrastructure.repository.SearchProductRetrofitRepository
import com.search.shared.MainDispatcherRule
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var searchRepository: SearchProductRetrofitRepository

    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun setUp() {
        Mockito.mockStatic(Log::class.java)
        searchViewModel = SearchViewModel(searchRepository, mainDispatcherRule.testDispatcher)
    }

    @Test
    fun getProductsBy_empty_screen_state() {
        //Arrange
        val query = "test"
        val emptyList = listOf<SearchProduct>()

        Mockito.`when`(searchRepository.searchProduct(query)).thenReturn(flowOf(emptyList))
        searchViewModel.onQueryChange(query)

        //Act
        searchViewModel.getProductsBy()

        //Assert
        val uiState = searchViewModel.uiState.value
        Assert.assertTrue(uiState is SearchScreenUiState.EMPTY)

    }

    @Test
    fun getProductsBy_empty_screen_state_and_searchField_idle_state() {
        //Arrange
        val query = "test"
        val emptyList = listOf<SearchProduct>()

        Mockito.`when`(searchRepository.searchProduct(query)).thenReturn(flowOf(emptyList))
        searchViewModel.onQueryChange(query)

        //Act
        searchViewModel.getProductsBy()

        //Assert
        val uiState = searchViewModel.uiState.value
        val searchFieldState = searchViewModel.searchFieldState.value
        Assert.assertTrue(uiState is SearchScreenUiState.EMPTY)
        Assert.assertTrue(searchFieldState is SearchFieldState.Idle)

    }

    @Test
    fun getProductsBy_throw_state_error() {
        //Arrange
        val query = "test"
        val exceptionMessage = "general exception"

        Mockito.`when`(searchRepository.searchProduct(query)).thenThrow(IllegalStateException(exceptionMessage))
        searchViewModel.onQueryChange(query)

        //Act
        searchViewModel.getProductsBy()

        //Assert
        val uiState = searchViewModel.uiState.value
        val searchFieldState = searchViewModel.searchFieldState.value
        Assert.assertTrue(uiState is SearchScreenUiState.ERROR)
        Assert.assertTrue(searchFieldState is SearchFieldState.Idle)

    }

    @Test
    fun getProductsBy_success_screen_state_and_searchField_idle_state() {
        //Arrange
        val query = "test"
        val listProduct = listOf(
            SearchProduct(
                "", "", "", 0,
                "", null, null
            )
        )

        Mockito.`when`(searchRepository.searchProduct(query)).thenReturn(flowOf(listProduct))
        searchViewModel.onQueryChange(query)

        //Act
        searchViewModel.getProductsBy()

        //Assert
        val uiState = searchViewModel.uiState.value
        val searchFieldState = searchViewModel.searchFieldState.value
        Assert.assertTrue(uiState is SearchScreenUiState.SUCCESS)
        Assert.assertTrue(searchFieldState is SearchFieldState.Idle)

    }

    @Test
    fun getProductsBy_success_screen_state_and_searchField_idle_state_verifyAddQuery_to_latestSearches() {
        //Arrange
        val query = "test"
        val listProduct = listOf(
            SearchProduct(
                "", "", "", 0,
                "", null, null
            )
        )

        Mockito.`when`(searchRepository.searchProduct(query)).thenReturn(flowOf(listProduct))
        searchViewModel.onQueryChange(query)

        //Act
        searchViewModel.getProductsBy()

        //Assert
        Assert.assertTrue(searchViewModel.latestSearches.value.contains(query))

    }

    @Test
    fun activateSearchField(){
        //Arrange

        //Act
        searchViewModel.searchFieldActivated()

        //Assert
        val searchFieldState = searchViewModel.searchFieldState.value
        Assert.assertTrue(searchFieldState is SearchFieldState.WithInputActive)

    }

    @Test
    fun searchQuery_notEmpty_then_clearQuery(){
        // Arrange
        val query = "test"

        // Act
        searchViewModel.onQueryChange(query)

        // Assert
        Assert.assertTrue(searchViewModel.query.value.isNotEmpty())
        Assert.assertEquals(query, searchViewModel.query.value)

        // Act
        searchViewModel.clearInput()

        // Assert
        Assert.assertTrue(searchViewModel.query.value.isEmpty())

    }

    @Test
    fun getProductsBy_keep_success_state_is_not_empty() {
        //Arrange
        val query = "test"
        val listProduct = listOf(
            SearchProduct(
                "", "", "", 0,
                "", null, null
            )
        )

        Mockito.`when`(searchRepository.searchProduct(query)).thenReturn(flowOf(listProduct))
        searchViewModel.onQueryChange(query)
        searchViewModel.getProductsBy()

        Assert.assertTrue(searchViewModel.uiState.value is SearchScreenUiState.SUCCESS)

        //Act
        searchViewModel.revertToInitialState()

        //Assert
        val uiState = searchViewModel.uiState.value
        Assert.assertTrue(uiState is SearchScreenUiState.SUCCESS)
        Assert.assertEquals(listProduct, (uiState as SearchScreenUiState.SUCCESS).items)

    }

}