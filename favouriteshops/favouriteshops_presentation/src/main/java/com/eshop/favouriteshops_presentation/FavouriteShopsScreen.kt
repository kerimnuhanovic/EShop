package com.eshop.favouriteshops_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.eshop.core.util.BASE_URL
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.components.ShopCard
import com.eshop.coreui.components.ProductCardPlaceholderFlowRow
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.util.UiEvent

@Composable
fun FavouriteShopsScreen(
    viewModel: FavouriteShopsViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    onNavigateBack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            if (uiEvent is UiEvent.Navigate) {
                onNavigate(uiEvent)
            } else if (uiEvent is UiEvent.NavigateBack) {
                onNavigateBack()
            }
        }
    }
    FavouriteShopsScreenContent(state = state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FavouriteShopsScreenContent(
    state: FavouriteShopsState,
    onEvent: (FavouriteShopsEvent) -> Unit
) {
    val dimensions = LocalDimensions.current
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = stringResource(id = R.string.favourite_shops),
                fontFamily = PoppinsFontFamily,
                fontSize = dimensions.font_16,
                color = MaterialTheme.colors.onSecondary
            )
        },
            navigationIcon = {
                IconButton(onClick = { onEvent(FavouriteShopsEvent.OnNavigateBack) }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            })
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(top = dimensions.spaceMedium)
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            if (state.isLoading) {
                ProductCardPlaceholderFlowRow()
            }
            FlowRow(modifier = Modifier.padding(horizontal = dimensions.spaceMedium)) {
                state.shops.forEachIndexed { index, shop ->
                    ShopCard(image = "$BASE_URL/${shop.profileImage}",
                        shopName = shop.username,
                        location = shop.shopLocations.first(),
                        review = 4.4,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(dimensions.uploadImageSurfaceSize)
                            .padding(
                                top = dimensions.spaceSmall,
                                bottom = dimensions.spaceSmall,
                                start = if (index % 2 == 0) dimensions.default else dimensions.spaceSmall
                            ),
                        onClick = {
                            onEvent(FavouriteShopsEvent.OnShopClick(shop.id))
                        })
                }
            }
        }
    }
}

@Composable
@Preview
private fun FavouriteShopsScreenContentPreview() {
    EShopTheme {
        FavouriteShopsScreenContent(state = FavouriteShopsState(isLoading = true), onEvent = {})
    }
}