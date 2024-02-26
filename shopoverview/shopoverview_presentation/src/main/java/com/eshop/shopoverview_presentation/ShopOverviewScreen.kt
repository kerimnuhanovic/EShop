package com.eshop.shopoverview_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.eshop.core.util.BASE_URL
import com.eshop.core.util.formatDate
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.R
import com.eshop.coreui.components.PrimaryTopBanner
import com.eshop.coreui.components.ProductCard
import com.eshop.coreui.components.ProductCardPlaceholderFlowRow
import com.eshop.coreui.components.ProductCardPlaceholderRow
import com.eshop.coreui.components.SearchBar
import com.eshop.coreui.components.TopBanner
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.util.ShopLocation
import com.eshop.coreui.util.UiEvent
import com.eshop.shopoverview_presentation.components.ShopCard

@Composable
fun ShopOverviewScreen(
    viewModel: ShopOverviewViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }

                UiEvent.NavigateBack -> {
                    // no-op
                }

                is UiEvent.ScrollPage -> {
                    // no-op
                }
            }
        }
    }
    ShopOverviewScreenContent(state = state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ShopOverviewScreenContent(
    state: ShopOverviewState,
    onEvent: (ShopOverviewEvent) -> Unit
) {
    val dimensions = LocalDimensions.current
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        PrimaryTopBanner(
            iconId = R.drawable.eshoplogo,
            inputText = state.searchQuery,
            onTextChange = {
                onEvent(ShopOverviewEvent.OnSearchQueryEnter(it))
            },
            isSingleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
            placeholderId = R.string.search,

        )
        Spacer(modifier = Modifier.height(dimensions.spaceMedium))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(dimensions.spaceLarge))
            Text(
                text = stringResource(id = com.eshop.shopoverview_presentation.R.string.popular_shops),
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = dimensions.font_20,
                modifier = Modifier.padding(start = dimensions.spaceMedium)
            )
            Spacer(modifier = Modifier.height(dimensions.spaceSmall))
            if (state.isPopularShopsLoading) {
                ProductCardPlaceholderRow()
            }
            LazyRow {
                itemsIndexed(state.popularShops) { index, shop ->
                    ShopCard(
                        image = "$BASE_URL/${shop.profileImage}",
                        shopName = shop.username,
                        location = shop.shopLocations.first(),
                        review = 4.4,
                        modifier = Modifier
                            .width(dimensions.card_width_180)
                            .height(dimensions.uploadImageSurfaceSize)
                            .padding(
                                top = dimensions.spaceSmall,
                                bottom = dimensions.spaceSmall,
                                start = if (index != 0) dimensions.spaceSmall else dimensions.spaceMedium,
                                end = if (index != state.popularShops.size.minus(1)) dimensions.default else dimensions.spaceMedium
                            ),
                        onClick = {
                            onEvent(ShopOverviewEvent.OnShopClick(shop.id))
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimensions.spaceMedium))
            Text(
                text = stringResource(id = com.eshop.shopoverview_presentation.R.string.all_shops),
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = dimensions.font_20,
                modifier = Modifier.padding(start = dimensions.spaceMedium)
            )
            Spacer(modifier = Modifier.height(dimensions.spaceSmall))
            if (state.isAllShopsLoading) {
                ProductCardPlaceholderFlowRow()
            }
            FlowRow(modifier = Modifier.padding(horizontal = dimensions.spaceMedium)) {
                state.allShops.forEachIndexed { index, shop ->
                    ShopCard(
                        image = "$BASE_URL/${shop.profileImage}",
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
                            onEvent(ShopOverviewEvent.OnShopClick(shop.id))
                        }
                    )
                }
            }
            if (state.areAllShopsLoaded) {
                Spacer(modifier = Modifier.height(dimensions.spaceExtraLarge))
            }
            if (state.isLoadingMoreShops) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(dimensions.spaceExtraLarge))
            }
            if (!scrollState.canScrollForward) {
                onEvent(ShopOverviewEvent.OnScreenEndReach)
            }
        }
    }
}

@Preview
@Composable
private fun ShopOverviewScreenPreview() {
    EShopTheme {
        ShopOverviewScreenContent(state = ShopOverviewState(), onEvent = {})
    }
}