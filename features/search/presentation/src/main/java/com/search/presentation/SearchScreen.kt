package com.search.presentation

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
fun SearchScreen(modifier: Modifier = Modifier, searchScreenUiState: SearchScreenUiState) {
    var text by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }
    var items = remember {
        mutableStateListOf<String>()
    }

    val allMovies = listOf(
        "Spider-Man: No Way Home",
        "Spider-Man: Into the Spider-Verse",
        "Spider-Man 2",
        "Spider-Man: Homecoming",
        "Spider-Man: Far From Home",
        "The Amazing Spider-Man",
        "Spider-Man 3"
    )

    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(query = text, onQueryChange = {
                text = it
            }, onSearch = {
                items.add(text)
                active = false
            }, active = active, onActiveChange = {
                active = it
            }, leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "search")
            },
                trailingIcon = {
                    if (active) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier.clickable {
                                if (text.isNotEmpty()) {
                                    text = ""
                                } else {
                                    active = false
                                }
                            })
                    }
                },
                placeholder = { Text(text = "Buscar") }, modifier = Modifier.padding(padding)
            ) {
                items.forEach { item ->
                    Row(modifier = Modifier.padding(14.dp)) {
                        Icon(
                            imageVector = Icons.Default.Create,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Text(text = item)
                    }
                }
            }
            when (searchScreenUiState) {
                is SearchScreenUiState.LOADING -> {
                    CircularProgressIndicator()
                }

                is SearchScreenUiState.SUCCESS -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(searchScreenUiState.items) { searchProduct ->
                            SearchProductItem(searchProduct = searchProduct)
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
fun SearchProductItem(modifier: Modifier = Modifier, searchProduct: SearchProduct) {
    ConstraintLayout(
        modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { }
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
    SearchScreen(searchScreenUiState = SearchScreenUiState.LOADING)
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
                shipping = null
            )
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
            )
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
            )
        )
    }

}