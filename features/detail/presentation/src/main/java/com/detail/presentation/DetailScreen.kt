package com.detail.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.detail.domain.model.Image
import com.detail.presentation.dot_indicator.DotIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier, uiState: DetailScreenUiState,
    onBackButton: () -> Unit,
    onRetry: () -> Unit,
) {
    Scaffold(modifier = modifier, topBar = {
        TopAppBar(title = { }, navigationIcon = {
            IconButton(onClick = onBackButton) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back")
            }
        })
    }) { padding ->

        when (uiState) {
            DetailScreenUiState.Initial -> Unit
            is DetailScreenUiState.LOADING -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }

            }

            is DetailScreenUiState.SUCCESS -> {
                ConstraintLayout(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp)
                ) {
                    val (banner, text, imagesComponent, originalPrice, price, percentageDiscount, descriptionComponent) = createRefs()
                    val item = uiState.product
                    BannerComponent(Modifier.constrainAs(banner) {
                        top.linkTo(parent.top, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }, item.condition)

                    Text(item.name, style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 18.sp, fontWeight = FontWeight.W400
                    ), maxLines = 3, modifier = Modifier.constrainAs(text) {
                        top.linkTo(banner.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    })

                    ImageComponent(modifier = Modifier.constrainAs(imagesComponent) {
                        start.linkTo(parent.start)
                        top.linkTo(text.bottom, margin = 8.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.ratio("16:9")

                    }, item.images)

                    Text(item.originalPrice.toString(),
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.LineThrough),
                        modifier = Modifier.constrainAs(originalPrice) {
                            top.linkTo(imagesComponent.bottom, 4.dp)
                            visibility =
                                if (item.originalPrice != null) Visibility.Visible else Visibility.Gone
                        })
                    Text(item.price.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.constrainAs(price) {
                            top.linkTo(originalPrice.bottom, 4.dp)
                        })
                    Text(text = "${item.calculateDiscount()}% OFF",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold, fontSize = 12.sp
                        ),
                        color = Color(0xFF00a650),
                        modifier = Modifier.constrainAs(percentageDiscount) {
                            start.linkTo(price.end, margin = 4.dp)
                            top.linkTo(price.top)
                            bottom.linkTo(price.bottom)
                            centerVerticallyTo(price)
                            visibility =
                                if (item.calculateDiscount() > 0) Visibility.Visible else Visibility.Gone
                        })


                    item.description?.let {
                        DescriptionComponent(modifier = Modifier.constrainAs(descriptionComponent) {
                            top.linkTo(price.bottom, 8.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }, description = it)
                    }

                }

            }

            is DetailScreenUiState.ERROR -> {
                when (uiState.error) {
                    is DetailScreenError.NotFound,
                    is DetailScreenError.ServerError,
                    is DetailScreenError.TechnicalError -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Ha ocurrido un error")

                            Button(onClick = onRetry) {
                                Text(text = "Intentar de nuevo")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BannerComponent(modifier: Modifier = Modifier, conditionText: String) {
    val greetingText = buildString {
        append(if (conditionText.contains("new", true)) "Nuevo" else "Usado")
    }

    Row(modifier = modifier) {
        Text(greetingText, style = MaterialTheme.typography.labelSmall, color = Color(0XFFD6D6D6))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ImageComponent(modifier: Modifier = Modifier, images: List<Image>) {
    val pagerState = rememberPagerState { images.size }
    Column(
        modifier = modifier.wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.weight(1f)) {
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                val image = images[page]
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image.imageUrl)
                        .build(),
                    contentDescription = image.id,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            Badge(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp),
                containerColor = Color(0XFFD6D6D6)
            ) {
                Text(
                    "${pagerState.currentPage.plus(1)} / ${images.size}",
                    modifier = Modifier.padding(2.dp)
                )
            }

            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(12.dp)
                    .clip(CircleShape)
                    .align(Alignment.TopEnd)
                    .background(Color(0XFFD6D6D6))
                    .size(34.dp)
            ) {
                Icon(
                    Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = Color(0XFF3483fa),
                    modifier = Modifier.size(18.dp)
                )
            }

            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 18.dp)
                    .clip(CircleShape)
                    .align(Alignment.BottomEnd)
                    .background(Color(0XFFD6D6D6))
                    .size(34.dp)
            ) {
                Icon(
                    Icons.Default.Share,
                    contentDescription = null,
                    tint = Color(0XFF3483fa),
                    modifier = Modifier.size(18.dp)
                )
            }

        }
        DotIndicator(
            pagerState = pagerState,
            count = pagerState.pageCount,
        )
    }
}

@Composable
private fun DescriptionComponent(modifier: Modifier = Modifier, description: String) {
    var isExpanded by remember { mutableStateOf(false) }
    val lineCount = remember(description) {
        description.lineCount()
    }
    Column(
        modifier = modifier.animateContentSize(
            animationSpec = tween(
                durationMillis = 500, easing = FastOutSlowInEasing
            )
        )
    ) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = if (isExpanded) Int.MAX_VALUE else 15,
            overflow = TextOverflow.Ellipsis
        )

        if (lineCount > 5) {
            Text(
                if (isExpanded) "Mostrar menos" else "Ver descripción completa",
                color = Color(0XFF3483fa),
                modifier = Modifier
                    .padding(vertical = 14.dp)
                    .clickable { isExpanded = !isExpanded }
            )
        }
    }
}

private fun String.lineCount(): Int {
    return this.split("\n").size
}

@Preview
@Composable
private fun DescriptionComponentPreview() {
    DescriptionComponent(description = "")
}

@Preview
@Composable
private fun ImageComponentPreview() {
    val modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)
    ImageComponent(modifier = modifier, listOf())
}

@Preview
@Composable
private fun BannerComponentPreview() {
    val modifier = Modifier.fillMaxWidth()
    BannerComponent(modifier = modifier, "new")
}

@Preview
@Composable
private fun DetailScreenPreview() {
    DetailScreen(uiState = DetailScreenUiState.Initial, onBackButton = {}, onRetry = {})
}