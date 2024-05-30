package com.eshop.shop_presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.eshop.core.util.BASE_URL
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.components.PageIndicator

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCatalog(
    pagerState: PagerState,
    images: List<String>,
    onBackClick: () -> Unit,
    isFavourite: Boolean,
    onFavouriteClick: () -> Unit,
    showPageIndicator: Boolean = true
) {
    val dimensions = LocalDimensions.current
    val verticalGradient = Brush.verticalGradient(
        listOf(Color.Black.copy(alpha = 0.3f), Color.Transparent),
        tileMode = TileMode.Clamp
    )
    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            HorizontalPager(
                state = pagerState,
                pageCount = images.size
            ) { page ->
                Box {
                    AsyncImage(
                        model = "$BASE_URL/${images[page]}",
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimensions.size_250),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimensions.size_50)
                            .background(brush = verticalGradient)
                    )
                }
            }
            if (showPageIndicator) {
                PageIndicator(
                    currentPage = pagerState.currentPage,
                    numberOfPages = images.size,
                    modifier = Modifier.padding(top = dimensions.spaceMedium)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensions.spaceMedium,
                    end = dimensions.spaceMedium,
                    top = dimensions.spaceMedium
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier.clickable {
                    onBackClick()
                },
                tint = Color.White
            )
            Icon(
                imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.clickable {
                    onFavouriteClick()
                }
            )

        }
    }
}