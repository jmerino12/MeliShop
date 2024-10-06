package com.search.presentation


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.search.domain.SearchProduct
import com.search.domain.Shipping

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchScreenUiState: SearchScreenUiState,
    searchFieldState: SearchFieldState,
    lastedSearches: List<String>,
    onSearchInputClicked: () -> Unit,
    onRevertInitialState: () -> Unit,
    onSearchInputChanged: (String) -> Unit,
    onClearInputClicked: () -> Unit,
    onSearch: () -> Unit,
    queryText: String,
    onProductClick: (String) -> Unit
) {
    Scaffold(modifier = modifier.fillMaxSize()) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(
                query = queryText,
                onQueryChange = {
                    onSearchInputChanged(it)
                },
                onSearch = {
                    onSearch()
                },
                active = searchFieldState is SearchFieldState.WithInputActive,
                onActiveChange = {
                    onSearchInputClicked()
                },
                leadingIcon = {
                    if (searchFieldState is SearchFieldState.WithInputActive) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back",
                            modifier = Modifier.clickable { onRevertInitialState() }
                        )
                    } else {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "search")

                    }
                },
                trailingIcon = {
                    if (searchFieldState is SearchFieldState.WithInputActive && queryText.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier.clickable {
                                onClearInputClicked()
                            })
                    }
                },
                placeholder = { Text(text = "Buscar") },
                modifier = Modifier.padding(padding)
            ) {
                lastedSearches.forEach { item ->
                    Row(
                        modifier = Modifier

                            .fillMaxWidth()
                            .padding(14.dp)
                            .clickable {
                                onSearchInputChanged(item)
                                onSearch()
                            }, verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_histoy),
                            contentDescription = "",
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Text(text = item)
                    }
                }
            }
            when (searchScreenUiState) {
                is SearchScreenUiState.Initial -> {}
                is SearchScreenUiState.LOADING -> {
                    CircularProgressIndicator()
                }

                is SearchScreenUiState.SUCCESS -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(searchScreenUiState.items) { searchProduct ->
                            SearchProductItem(
                                searchProduct = searchProduct,
                                onProductClick = onProductClick
                            )
                        }
                    }
                }

                is SearchScreenUiState.EMPTY -> {
                    Text(text = "No hay nada para visualizar")
                }

                is SearchScreenUiState.ERROR -> {
                    Text(text = "Ha ocurrido un error")
                }
            }

        }

    }

}

@Composable
fun SearchProductItem(
    modifier: Modifier = Modifier, searchProduct: SearchProduct,
    onProductClick: (String) -> Unit
) {
    ConstraintLayout(
        modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onProductClick(searchProduct.id)
            }
    ) {
        val (
            image,
            title,
            fullPrice,
            discountPrice,
            percentageDiscount,
            freeDelivery
        ) = createRefs()

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(searchProduct.thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = searchProduct.name,
            modifier = Modifier
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth(0.4f)
                .aspectRatio(1f)
        )

        Text(
            text = searchProduct.name,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(image.end, margin = 8.dp)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )


        Text(
            text = searchProduct.originalPrice.toString(),
            style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.LineThrough),
            color = Color.Gray,
            modifier = Modifier
                .constrainAs(fullPrice) {
                    start.linkTo(title.start)
                    top.linkTo(title.bottom, margin = 4.dp)
                    visibility =
                        if (searchProduct.originalPrice != null) Visibility.Visible else Visibility.Gone
                }
        )


        Text(
            text = searchProduct.price.toString(),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            color = Color.Black,
            modifier = Modifier
                .constrainAs(discountPrice) {
                    start.linkTo(title.start)
                    if (searchProduct.originalPrice != null) {
                        top.linkTo(fullPrice.bottom, margin = 4.dp)
                    } else {
                        top.linkTo(title.bottom, margin = 4.dp)
                    }
                    end.linkTo(percentageDiscount.start)
                }
        )


        Text(
            text = "${searchProduct.calculateDiscount()}% OFF",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            ),
            color = Color(0xFF00a650),
            modifier = Modifier
                .constrainAs(percentageDiscount) {
                    start.linkTo(discountPrice.end, margin = 4.dp)
                    top.linkTo(discountPrice.top)
                    bottom.linkTo(discountPrice.bottom)
                    centerVerticallyTo(discountPrice)
                    visibility =
                        if (searchProduct.calculateDiscount() > 0) Visibility.Visible else Visibility.Gone
                }
        )

        Text(
            text = if (searchProduct.shipping?.isFreeShipping == true) "Envio Gratis" else "",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,

                ),
            color = Color(0xFF00a650),
            modifier = Modifier
                .constrainAs(freeDelivery) {
                    top.linkTo(discountPrice.bottom, margin = 4.dp)
                    start.linkTo(image.end, margin = 8.dp)
                    visibility =
                        if (searchProduct.shipping?.isFreeShipping == true) Visibility.Visible else Visibility.Gone
                }
        )


    }

}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        searchScreenUiState = SearchScreenUiState.LOADING,
        onSearchInputChanged = {},
        onSearchInputClicked = {},
        searchFieldState = SearchFieldState.Idle,
        queryText = "",
        onClearInputClicked = {},
        lastedSearches = listOf(),
        onSearch = {},
        onRevertInitialState = {},
        onProductClick = {}
    )
}

@Preview
@Composable
private fun SearchProductItemPreview() {
    Column {
        SearchProductItem(
            searchProduct = SearchProduct(
                "0",
                "LOREM LOREM LOREMP LORM",
                condition = "NEW",
                thumbnail = "",
                price = 1200000,
                originalPrice = 1500000,
                shipping = null,
            ),
            onProductClick = {}
        )
        SearchProductItem(
            searchProduct = SearchProduct(
                "0",
                "LOREM LOREM LOREMP LORM",
                condition = "NEW",
                thumbnail = "",
                price = 1203233,
                originalPrice = null,
                shipping = null
            ),
            onProductClick = {}
        )

        SearchProductItem(
            searchProduct = SearchProduct(
                "0",
                "LOREM LOREM LOREMP LORM",
                condition = "NEW",
                thumbnail = "",
                price = 1203233,
                originalPrice = null,
                shipping = Shipping(true)
            ),
            onProductClick = {}
        )
    }

}