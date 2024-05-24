package com.eshop.shopoverview_presentation

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import com.eshop.core.util.BASE_URL
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.R
import com.eshop.coreui.components.BottomBar
import com.eshop.coreui.components.DrawerItem
import com.eshop.coreui.components.EShopButton
import com.eshop.coreui.components.EShopCheckbox
import com.eshop.coreui.components.PrimarySearchBar
import com.eshop.coreui.components.ProductCardPlaceholderFlowRow
import com.eshop.coreui.components.ProductCardPlaceholderRow
import com.eshop.coreui.components.TopBanner
import com.eshop.coreui.navigation.Route
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.util.BottomBarItem
import com.eshop.coreui.util.UiEvent
import com.eshop.shopoverview_presentation.components.ShopCard
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ShopOverviewScreen(
    viewModel: ShopOverviewViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
) {
    val state = viewModel.state.collectAsState().value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val focusRequester = remember {
        FocusRequester()
    }

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

                UiEvent.ChangeNavigationDrawerState -> {
                    scope.launch {
                        if (scaffoldState.drawerState.isOpen) {
                            scaffoldState.drawerState.close()
                        } else {
                            scaffoldState.drawerState.open()
                        }
                    }
                }

                UiEvent.FocusInputField -> {
                    focusRequester.requestFocus()
                }

                is UiEvent.DisplayToast -> {
                    // no-op
                }

                UiEvent.DataLoaded -> {
                    // no-op
                }

                UiEvent.CloseBottomSheet -> {
                    // no-op
                }
            }
        }
    }
    ShopOverviewScreenContent(
        state = state,
        scaffoldState = scaffoldState,
        focusRequester = focusRequester,
        onEvent = viewModel::onEvent,
        onNavigate = onNavigate
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ShopOverviewScreenContent(
    state: ShopOverviewState,
    scaffoldState: ScaffoldState,
    focusRequester: FocusRequester,
    onEvent: (ShopOverviewEvent) -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val dimensions = LocalDimensions.current
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val isBottomBarOverlapped = remember {
        mutableStateOf(false)
    }

    val bottomBarHeightPx = with(LocalDensity.current) { dimensions.size_56.roundToPx().toFloat() }
    val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }
    val topBarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = bottomBarOffsetHeightPx.value + delta

                bottomBarOffsetHeightPx.value = newOffset.coerceIn(-bottomBarHeightPx, 0f)
                topBarOffsetHeightPx.value = newOffset.coerceIn(-bottomBarHeightPx, 0f)

                return Offset.Zero
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection),
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomBar(
                items = listOf(
                    BottomBarItem(text = "Products", icon = Icons.Rounded.List, route = Route.PRODUCTS_OVERVIEW),
                    BottomBarItem(text = "Shops", iconId = R.drawable.shopping_basket_24, route = Route.SHOPS_OVERVIEW),
                    BottomBarItem(text = "Message", iconId = R.drawable.message_24, route = Route.CONVERSATIONS),
                    BottomBarItem(text = "Basket", icon = Icons.Rounded.ShoppingCart, route = Route.BASKET),
                    BottomBarItem(text = "Orders", icon = Icons.Rounded.Settings, route = Route.ORDERS)
                ),
                isBottomBarOverlapped = isBottomBarOverlapped.value,
                onNavigate = onNavigate,
                currentDestination = Route.SHOPS_OVERVIEW,
                modifier = Modifier.offset {
                    IntOffset(
                        x = 0,
                        y = -bottomBarOffsetHeightPx.value.roundToInt()
                    )
                }
            )
        },
        drawerContent = {
            Divider()
            DrawerItem(
                isDrawerItemExpanded = state.isFilterDrawerItemExpanded,
                containerModifier = Modifier.padding(
                    horizontal = dimensions.spaceMedium,
                    vertical = dimensions.spaceSmall
                ),
                label = {
                    Text(
                        text = stringResource(id = R.string.filter),
                        fontSize = dimensions.font_20,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        color = Color.Black
                    )
                },
                iconColor = Color.Black,
                onIconClick = {
                    onEvent(ShopOverviewEvent.OnFilterDrawerItemClick)
                },
                iconModifier = Modifier.size(dimensions.size_32)
            ) {
                Column(modifier = Modifier.padding(top = dimensions.spaceSmall)) {
                    state.shopCategories.map { shopCategory ->
                        EShopCheckbox(isChecked = shopCategory.isSelected, onChange = {
                            onEvent(ShopOverviewEvent.OnShopCategorySelect(shopCategory))
                        }, label = {
                            Text(
                                text = shopCategory.category.value,
                                fontFamily = PoppinsFontFamily,
                                fontSize = dimensions.font_16,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black.copy(0.8f),
                                modifier = Modifier.padding(horizontal = dimensions.spaceExtraSmall)
                            )
                        },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colors.primary,
                                checkmarkColor = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
                    }
                }
            }
            Divider()

            DrawerItem(
                isDrawerItemExpanded = state.isSortDrawerItemExpanded,
                containerModifier = Modifier.padding(
                    horizontal = dimensions.spaceMedium,
                    vertical = dimensions.spaceSmall
                ),
                label = {
                    Text(
                        text = stringResource(id = R.string.sort_by),
                        fontSize = dimensions.font_20,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        color = Color.Black
                    )
                },
                iconColor = Color.Black,
                onIconClick = {
                    onEvent(ShopOverviewEvent.OnSortDrawerItemClick)
                },
                iconModifier = Modifier.size(dimensions.size_32)
            ) {
                Column(modifier = Modifier.padding(top = dimensions.spaceSmall)) {
                    state.sortCriteria.map { criterionItem ->
                        EShopCheckbox(isChecked = criterionItem.isSelected, onChange = {
                            onEvent(ShopOverviewEvent.OnSortCriterionSelect(criterionItem))
                        }, label = {
                            Text(
                                text = stringResource(id = criterionItem.criterion.labelId),
                                fontFamily = PoppinsFontFamily,
                                fontSize = dimensions.font_16,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black.copy(0.8f),
                                modifier = Modifier.padding(horizontal = dimensions.spaceExtraSmall)
                            )
                        },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colors.primary,
                                checkmarkColor = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
                    }
                }
            }
            Divider()
            Spacer(modifier = Modifier.weight(1f))
            EShopButton(
                content = {
                    Text(
                        text = stringResource(id = R.string.done),
                        fontSize = dimensions.font_16,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Medium
                    )
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                shape = RoundedCornerShape(dimensions.spaceSmall),
                onButtonClick = { onEvent(ShopOverviewEvent.OnFilterClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensions.spaceMedium)
                    .height(dimensions.size_50)
            )
        },
        drawerBackgroundColor = MaterialTheme.colors.onPrimary
    ) { values ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .verticalScroll(scrollState)
                .padding(top = dimensions.size_60)
        ) {
            Spacer(modifier = Modifier.height(dimensions.spaceMedium))
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
                    ShopCard(image = "$BASE_URL/${shop.profileImage}",
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
                        })
                }
            }
            Spacer(modifier = Modifier.height(dimensions.spaceMedium))
            Text(
                text = if (state.areSearchedShopsDisplayed) "Search results for '${state.searchedQuery}'" else stringResource(
                    id = if (state.shopCategories.count { it.isSelected } > 0) com.eshop.shopoverview_presentation.R.string.filtered_shops else com.eshop.shopoverview_presentation.R.string.all_shops
                ),
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
                            onEvent(ShopOverviewEvent.OnShopClick(shop.id))
                        })
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
        Box {
            androidx.compose.animation.AnimatedVisibility(
                visible = state.isSearchBarVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 300)),
                exit = fadeOut(animationSpec = tween(durationMillis = 150))
            ) {
                PrimarySearchBar(inputText = state.searchQuery, onTextChange = {
                    onEvent(ShopOverviewEvent.OnSearchQueryEnter(it))
                }, isSingleLine = true, keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ), keyboardActions = KeyboardActions(onSearch = {
                    keyboardController?.hide()
                    onEvent(ShopOverviewEvent.OnSearch)
                }), placeholderId = R.string.search, onLeadingIconClick = {
                    onEvent(ShopOverviewEvent.OnExitSearchBarClick)
                }, onTrailingIconClick = {
                    onEvent(ShopOverviewEvent.OnDeleteSearchTextClick)
                },
                    focusRequester = focusRequester,
                    modifier = Modifier.offset {
                        IntOffset(
                            x = 0,
                            y = topBarOffsetHeightPx.value.roundToInt()
                        )
                    }
                )
            }
            androidx.compose.animation.AnimatedVisibility(
                visible = !state.isSearchBarVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 300)),
                exit = fadeOut(animationSpec = tween(durationMillis = 150))
            ) {
                TopBanner(iconId = R.drawable.eshoplogo,
                    titleId = R.string.eshop,
                    subtitleId = R.string.your_online_shop_destination,
                    onSearchIconClick = {
                        onEvent(ShopOverviewEvent.OnSearchIconClick)
                    },
                    onFilterIconClick = { onEvent(ShopOverviewEvent.OnFilterIconClick) },
                    modifier = Modifier.offset {
                        IntOffset(
                            x = 0,
                            y = topBarOffsetHeightPx.value.roundToInt()
                        )
                    }
                )
            }
        }
    }

}

@Preview
@Composable
private fun ShopOverviewScreenPreview() {
    EShopTheme {
        ShopOverviewScreenContent(
            scaffoldState = rememberScaffoldState(),
            state = ShopOverviewState(
                isAllShopsLoading = true,
                isPopularShopsLoading = true
            ), onEvent = {}, onNavigate = {}, focusRequester = FocusRequester())
    }
}