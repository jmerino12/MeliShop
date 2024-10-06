package com.detail.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.detail.presentation.dot_indicator.DotIndicator

@Composable
fun DetailScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier) { padding ->
        ConstraintLayout(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            val (banner, text, imagesComponent, originalPrice, price, percentageDiscount,
                freeDelivery, descriptionComponent) = createRefs()
            BannerComponent(
                Modifier
                    .constrainAs(banner) {
                        top.linkTo(parent.top, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )
            Text("Macbook Pro 14 Chip M3 Pro 18 GB de Ram 512GB SSD Plateada Apple 14-Core 120 Hz 3024x1964px MacOS",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W400
                ),
                maxLines = 3,
                modifier = Modifier.constrainAs(text) {
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

            })

            Text(
                "$ 4.387.365",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.LineThrough),
                modifier = Modifier.constrainAs(originalPrice) {
                    top.linkTo(imagesComponent.bottom, 4.dp)
                }
            )
            Text(
                "$ 3.190.000",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.constrainAs(price) {
                    top.linkTo(originalPrice.bottom, 4.dp)
                }
            )
            Text(
                text = "27% OFF",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                ),
                color = Color(0xFF00a650),
                modifier = Modifier
                    .constrainAs(percentageDiscount) {
                        start.linkTo(price.end, margin = 4.dp)
                        top.linkTo(price.top)
                        bottom.linkTo(price.bottom)
                        centerVerticallyTo(price)
                    }
            )

            Text(
                text = "Envio Gratis",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,

                    ),
                color = Color(0xFF00a650),
                modifier = Modifier
                    .constrainAs(freeDelivery) {
                        top.linkTo(price.bottom, margin = 4.dp)
                        start.linkTo(price.start)
                    }
            )

            DescriptionComponent(modifier = Modifier.constrainAs(descriptionComponent) {
                top.linkTo(freeDelivery.bottom, 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

        }
    }
}

@Composable
private fun BannerComponent(modifier: Modifier = Modifier) {
    val greetingText = buildString {
        append("Nuevo")
        append(" | ")
        append("+5 vendidos")
    }

    Row(modifier = modifier) {
        Text(greetingText)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ImageComponent(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState { 3 }
    Column(modifier = modifier.wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.weight(1f)) {
            Badge(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp),
                containerColor = Color(0XFFD6D6D6)
            ) {
                Text("1 / 7", modifier = Modifier.padding(2.dp))
            }

            IconButton(
                onClick = {}, modifier = Modifier
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
                onClick = {}, modifier = Modifier
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

            HorizontalPager(state = pagerState, modifier = modifier) {

            }

        }
        DotIndicator(
            pagerState = pagerState, count = pagerState.pageCount
        )
    }
}

@Composable
private fun DescriptionComponent(modifier: Modifier = Modifier) {
    var isExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            )
    ) {
        Text(
            text = "La notebook más delgada y ligera de Apple viene con los superpoderes del chip M1. Termina todos tus proyectos mucho más rápido con el CPU de 8 núcleos y disfruta como nunca antes de apps y juegos con gráficos avanzados gracias al GPU de hasta 8 núcleos. Además, el Neural Engine de 16 núcleos se encarga de acelerar todos los procesos de aprendizaje automático. Todo en un diseño silencioso sin ventilador que te ofrece la mayor duración de batería en una MacBook Air: hasta 18 horas. (1) Portátil como siempre, más poderosa que nunca.\n" +
                    "\n" +
                    "\n" +
                    "Avisos Legales\n" +
                    "No todos los recursos y configuraciones están disponibles en todos los países.\n" +
                    "(1) La duración de la batería varía según el uso y la configuración.\n" +
                    "(2) Comparado con la generación anterior.\n" +
                    "(3) El tamaño de la pantalla se mide en diagonal.\n" +
                    "\n" +
                    "Aviso legal\n" +
                    "• La duración de la batería depende del uso que se le dé al producto.",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = if (isExpanded) Int.MAX_VALUE else 15,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            if (isExpanded) "Mostrar menos" else "Ver descripción completa",
            color = Color(0XFF3483fa),
            modifier = Modifier
                .padding(vertical = 14.dp)
                .clickable { isExpanded = !isExpanded })
    }
}

@Preview
@Composable
private fun DescriptionComponentPreview() {
    DescriptionComponent()
}

@Preview
@Composable
private fun ImageComponentPreview() {
    val modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)
    ImageComponent(modifier = modifier)
}

@Preview
@Composable
private fun BannerComponentPreview() {
    val modifier = Modifier.fillMaxWidth()
    BannerComponent(modifier = modifier)
}

@Preview
@Composable
private fun DetailScreenPreview() {
    DetailScreen()
}