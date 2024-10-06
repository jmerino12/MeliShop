package com.exmaple.melishop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.detail.presentation.DetailScreen
import com.detail.presentation.ProductDetailViewModel
import com.exmaple.melishop.ui.theme.MeliShopTheme
import com.search.presentation.SearchScreen
import com.search.presentation.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeliShopTheme {
                MeliNavHost()
            }
        }
    }
}

@Composable
private fun MeliNavHost(
    navHostController: NavHostController = rememberNavController(),
) {
    NavHost(navHostController, startDestination = "search") {
        composable("search") {
            val viewModel = hiltViewModel<SearchViewModel>()
            val uiSate by viewModel.uiState.collectAsState()
            val searchFieldState by viewModel.searchFieldState.collectAsState()
            val queryText by viewModel.query.collectAsState()
            val lastedSearches by viewModel.latestSearches.collectAsState()
            SearchScreen(
                searchScreenUiState = uiSate,
                searchFieldState = searchFieldState,
                queryText = queryText, lastedSearches = lastedSearches,
                onSearchInputChanged = { query -> viewModel.onQueryChange(query) },
                onRevertInitialState = { viewModel.revertToInitialState() },
                onClearInputClicked = { viewModel.clearInput() },
                onSearchInputClicked = { viewModel.searchFieldActivated() },
                onSearch = { viewModel.getProductsBy() },
                onProductClick = { productId ->
                    navHostController.navigate("detail/$productId")
                }
            )
        }

        composable(
            "detail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel = hiltViewModel<ProductDetailViewModel>()
            backStackEntry.arguments?.getString("productId")
            val uiState by viewModel.uiState.collectAsState()
            DetailScreen(
                uiState = uiState,
                onBackButton = { navHostController.popBackStack() },
                onRetry = viewModel::onRetry
            )
        }
    }
}