package com.eshop.productoverview_presentation

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import com.eshop.core.util.BASE_URL
import com.eshop.core.util.formatDate
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.R
import com.eshop.coreui.components.BottomBar
import com.eshop.coreui.components.DrawerItem
import com.eshop.coreui.components.EShopButton
import com.eshop.coreui.components.EShopCheckbox
import com.eshop.coreui.components.EShopDropdownMenu
import com.eshop.coreui.components.ErrorBox
import com.eshop.coreui.components.FloatingButton
import com.eshop.coreui.components.ImageHolder
import com.eshop.coreui.components.ImageUploadPlaceholder
import com.eshop.coreui.components.InputField
import com.eshop.coreui.components.ItemDataBox
import com.eshop.coreui.components.PrimarySearchBar
import com.eshop.coreui.components.ProductCard
import com.eshop.coreui.components.ProductCardPlaceholderFlowRow
import com.eshop.coreui.components.ProductCardPlaceholderRow
import com.eshop.coreui.components.TopBanner
import com.eshop.coreui.navigation.Route
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.util.BottomBarItem
import com.eshop.coreui.util.ShopAndProductCategory
import com.eshop.coreui.util.UiEvent
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ProductOverviewScreen(
    viewModel: ProductOverviewViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    onDataLoaded: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val focusRequester = remember {
        FocusRequester()
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            if (uiEvent is UiEvent.Navigate) {
                onNavigate(uiEvent)
            } else if (uiEvent == UiEvent.FocusInputField) {
                focusRequester.requestFocus()
            } else if (uiEvent == UiEvent.ChangeNavigationDrawerState) {
                scope.launch {
                    if (scaffoldState.drawerState.isOpen) {
                        scaffoldState.drawerState.close()
                    } else {
                        scaffoldState.drawerState.open()
                    }
                }
            } else if (uiEvent is UiEvent.DataLoaded) {
                onDataLoaded()
            }

        }
    }
    ProductOverviewScreenContent(
        state = state,
        scaffoldState = scaffoldState,
        onEvent = viewModel::onEvent,
        onNavigate = onNavigate,
        focusRequester = focusRequester
    )

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
private fun ProductOverviewScreenContent(
    state: ProductOverviewState,
    scaffoldState: ScaffoldState,
    focusRequester: FocusRequester,
    onEvent: (ProductOverviewEvent) -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val dimensions = LocalDimensions.current
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uriList ->
            uriList.map {
                onEvent(ProductOverviewEvent.OnProductPhotosSelect(uriList))
            }
        }
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val isBottomBarOverlapped = remember {
        mutableStateOf(false)
    }
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {
            if (it == ModalBottomSheetValue.Hidden)
                isBottomBarOverlapped.value = false
            true
        },
        skipHalfExpanded = true
    )

    val bottomBarHeightPx = with(LocalDensity.current) { dimensions.size_56.roundToPx().toFloat() }
    val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }
    val topBarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                return if (!isBottomBarOverlapped.value) {
                    val delta = available.y
                    val newOffset = bottomBarOffsetHeightPx.value + delta

                    bottomBarOffsetHeightPx.value = newOffset.coerceIn(-bottomBarHeightPx, 0f)
                    topBarOffsetHeightPx.value = newOffset.coerceIn(-bottomBarHeightPx, 0f)
                    Offset.Zero
                } else {
                    Offset.Zero
                }
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
                    BottomBarItem(text = "Dashboard", icon = Icons.Rounded.Settings, route = Route.DASHBOARD)
                ),
                isBottomBarOverlapped = isBottomBarOverlapped.value,
                onNavigate = onNavigate,
                currentDestination = Route.PRODUCTS_OVERVIEW,
                modifier = Modifier.offset { IntOffset(x = 0, y = -bottomBarOffsetHeightPx.value.roundToInt()) }
            )
        },
        floatingActionButton = {
            FloatingButton(
                modalBottomSheetState = bottomSheetState,
                isBottomBarOverlapped = isBottomBarOverlapped
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
                    onEvent(ProductOverviewEvent.OnFilterDrawerItemClick)
                },
                iconModifier = Modifier.size(dimensions.size_32)
            ) {
                Column(modifier = Modifier.padding(top = dimensions.spaceSmall)) {
                    state.productCategories.map { productCategory ->
                        EShopCheckbox(isChecked = productCategory.isSelected, onChange = {
                            onEvent(ProductOverviewEvent.OnProductCategorySelect(productCategory))
                        }, label = {
                            Text(
                                text = productCategory.category.value,
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
                    onEvent(ProductOverviewEvent.OnSortDrawerItemClick)
                },
                iconModifier = Modifier.size(dimensions.size_32)
            ) {
                Column(modifier = Modifier.padding(top = dimensions.spaceSmall)) {
                    state.sortCriteria.map { criterionItem ->
                        EShopCheckbox(isChecked = criterionItem.isSelected, onChange = {
                            onEvent(ProductOverviewEvent.OnSortCriterionSelect(criterionItem))
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
                onButtonClick = { onEvent(ProductOverviewEvent.OnFilterClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensions.spaceMedium)
                    .height(dimensions.size_50)
            )
        },
        drawerBackgroundColor = MaterialTheme.colors.onPrimary
    ) {
        ModalBottomSheetLayout(
            sheetState = bottomSheetState,
            sheetContent = {
                Column(
                    modifier = Modifier
                        .padding(dimensions.spaceMedium)
                        .fillMaxHeight(0.9f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                    Text(
                        text = stringResource(id = com.eshop.productoverview_presentation.R.string.add_product),
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = dimensions.font_20,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                    if (state.errorMessageId != null) {
                        ErrorBox(
                            errorMessageId = state.errorMessageId,
                        )
                        Spacer(modifier = Modifier.height(dimensions.spaceLarge))
                    }
                    Text(
                        text = stringResource(id = com.eshop.productoverview_presentation.R.string.title),
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = dimensions.font_16,
                        color = Color.Black,
                        modifier = Modifier.padding(
                            start = dimensions.spaceSmall,
                            bottom = dimensions.spaceExtraSmall
                        )
                    )
                    InputField(
                        inputText = state.productTitle,
                        onTextChange = {
                            onEvent(ProductOverviewEvent.OnProductTitleEnter(it))
                        },
                        placeholderId = com.eshop.productoverview_presentation.R.string.enter_product_title
                    )
                    Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                    Text(
                        text = stringResource(id = com.eshop.productoverview_presentation.R.string.description),
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = dimensions.font_16,
                        color = Color.Black,
                        modifier = Modifier.padding(
                            start = dimensions.spaceSmall,
                            bottom = dimensions.spaceExtraSmall
                        )
                    )
                    InputField(
                        inputText = state.productDescription,
                        onTextChange = {
                            onEvent(ProductOverviewEvent.OnProductDescriptionEnter(it))
                        },
                        placeholderId = com.eshop.productoverview_presentation.R.string.enter_product_description
                    )
                    Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                    Text(
                        text = stringResource(id = com.eshop.productoverview_presentation.R.string.price),
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = dimensions.font_16,
                        color = Color.Black,
                        modifier = Modifier.padding(
                            start = dimensions.spaceSmall,
                            bottom = dimensions.spaceExtraSmall
                        )
                    )
                    InputField(
                        inputText = state.productPrice,
                        onTextChange = {
                            onEvent(ProductOverviewEvent.OnProductPriceEnter(it))
                        },
                        placeholderId = com.eshop.productoverview_presentation.R.string.enter_product_price
                    )
                    Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                    Text(
                        text = stringResource(id = com.eshop.productoverview_presentation.R.string.select_your_product_category),
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = dimensions.font_16,
                        color = Color.Black,
                        modifier = Modifier.padding(
                            start = dimensions.spaceSmall,
                            bottom = dimensions.spaceExtraSmall
                        )
                    )
                    EShopDropdownMenu(
                        expanded = state.isCategoryDropdownMenuExpanded,
                        placeholder = stringResource(id = com.eshop.productoverview_presentation.R.string.select_your_product_category),
                        items = ShopAndProductCategory.listAllCategories(),
                        selectedItems = state.listOfProductCategories,
                        onExpandChange = {
                            onEvent(ProductOverviewEvent.OnExpandChange)
                        },
                        onSelectItem = {
                            onEvent(ProductOverviewEvent.OnProductCategoryClick(it as ShopAndProductCategory))
                        }
                    )
                    FlowRow {
                        state.listOfProductCategories.forEach {
                            ItemDataBox(itemData = it, onExitClick = { productCategory ->
                                onEvent(ProductOverviewEvent.OnProductCategoryClick(productCategory as ShopAndProductCategory))
                            })
                        }
                    }
                    Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                    Text(
                        text = stringResource(id = com.eshop.productoverview_presentation.R.string.upload_product_photos),
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = dimensions.font_16,
                        color = Color.Black,
                        modifier = Modifier.padding(
                            start = dimensions.spaceSmall,
                            bottom = dimensions.spaceExtraSmall
                        )
                    )
                    Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                    FlowRow(
                        modifier = Modifier
                            .padding(
                                start = dimensions.spaceSmall,
                                end = dimensions.spaceSmall,
                                bottom = dimensions.spaceExtraSmall
                            ),
                        horizontalArrangement = Arrangement.spacedBy(dimensions.spaceMedium)
                    ) {
                        state.productImages.forEach { image ->
                            Column {
                                Box(modifier = Modifier.size(dimensions.size_100)) {
                                    ImageHolder(onExitClick = {
                                        onEvent(
                                            ProductOverviewEvent.OnProductPhotoRemove(
                                                image
                                            )
                                        )
                                    }, image = image)
                                }
                                Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                            }
                        }
                        ImageUploadPlaceholder(onClick = { galleryLauncher.launch("image/*") })
                    }
                    Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                    EShopButton(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onPrimary,
                        onButtonClick = {
                            onEvent(ProductOverviewEvent.OnAddProductClick)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimensions.buttonHeight_56),
                        shape = RectangleShape,
                        content = {
                            if (!state.isProductAdditionInProgress) {
                                Text(
                                    text = stringResource(id = com.eshop.productoverview_presentation.R.string.add),
                                    fontFamily = PoppinsFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = dimensions.font_16
                                )
                            } else {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colors.onPrimary,
                                    modifier = Modifier.size(dimensions.size_32)
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                }
            },
            sheetShape = RoundedCornerShape(
                topStart = dimensions.spaceMedium,
                topEnd = dimensions.spaceMedium
            ),
            sheetBackgroundColor = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .verticalScroll(scrollState)
                    .padding(top = dimensions.size_60)
            ) {
                Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                Text(
                    text = stringResource(id = com.eshop.productoverview_presentation.R.string.popular_products),
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = dimensions.font_20,
                    modifier = Modifier.padding(start = dimensions.spaceMedium)
                )
                Spacer(modifier = Modifier.height(dimensions.spaceSmall))
                if (state.isPopularProductsLoading) {
                    ProductCardPlaceholderRow()
                }
                LazyRow {
                    itemsIndexed(state.popularProducts) { index, product ->
                        ProductCard(
                            image = "${BASE_URL}/${product.images.first()}",
                            productName = product.title,
                            price = product.price,
                            date = product.date.formatDate(),
                            modifier = Modifier
                                .width(dimensions.card_width_180)
                                .height(dimensions.uploadImageSurfaceSize)
                                .padding(
                                    top = dimensions.spaceSmall,
                                    bottom = dimensions.spaceSmall,
                                    start = if (index != 0) dimensions.spaceSmall else dimensions.spaceMedium,
                                    end = if (index != state.popularProducts.size.minus(1)) dimensions.default else dimensions.spaceMedium
                                ),
                            onClick = {
                                onEvent(ProductOverviewEvent.OnProductClick(product.id))
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                Text(
                    text = if (state.areSearchedSProductsDisplayed) "Search results for '${state.searchedQuery}'" else stringResource(
                        id = if (state.productCategories.count { it.isSelected } > 0) com.eshop.productoverview_presentation.R.string.filtered_products else com.eshop.productoverview_presentation.R.string.all_products
                    ),
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = dimensions.font_20,
                    modifier = Modifier.padding(start = dimensions.spaceMedium)
                )
                Spacer(modifier = Modifier.height(dimensions.spaceSmall))
                if (state.isAllProductsLoading) {
                    ProductCardPlaceholderFlowRow()
                }
                FlowRow(modifier = Modifier.padding(horizontal = dimensions.spaceMedium)) {
                    state.allProducts.forEachIndexed { index, product ->
                        ProductCard(
                            image = "${BASE_URL}/${product.images.first()}",
                            productName = product.title,
                            price = product.price,
                            date = product.date.formatDate(),
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(dimensions.uploadImageSurfaceSize)
                                .padding(
                                    top = dimensions.spaceSmall,
                                    bottom = dimensions.spaceSmall,
                                    start = if (index % 2 == 0) dimensions.default else dimensions.spaceSmall
                                ),
                            onClick = {
                                onEvent(ProductOverviewEvent.OnProductClick(product.id))
                            }
                        )
                    }
                }
                if (state.areAllProductsLoaded) {
                    Spacer(modifier = Modifier.height(dimensions.spaceExtraLarge))
                }
                if (state.isLoadingMoreProducts) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    Spacer(modifier = Modifier.height(dimensions.spaceExtraLarge))
                }
                if (!scrollState.canScrollForward) {
                    onEvent(ProductOverviewEvent.OnScreenEndReach)
                }
            }

            Box(modifier = Modifier.offset { IntOffset(x = 0, y = topBarOffsetHeightPx.value.roundToInt()) }) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = state.isSearchBarVisible,
                    enter = fadeIn(animationSpec = tween(durationMillis = 300)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 150))
                ) {
                    PrimarySearchBar(inputText = state.searchQuery, onTextChange = {
                        onEvent(ProductOverviewEvent.OnSearchQueryEnter(it))
                    }, isSingleLine = true, keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ), keyboardActions = KeyboardActions(onSearch = {
                        keyboardController?.hide()
                        onEvent(ProductOverviewEvent.OnSearch)
                    }), placeholderId = R.string.search, onLeadingIconClick = {
                        onEvent(ProductOverviewEvent.OnExitSearchBarClick)
                    }, onTrailingIconClick = {
                        onEvent(ProductOverviewEvent.OnDeleteSearchTextClick)
                    }, focusRequester = focusRequester
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
                            keyboardController?.show()
                            onEvent(ProductOverviewEvent.OnSearchIconClick)
                        },
                        onFilterIconClick = { onEvent(ProductOverviewEvent.OnFilterIconClick) })
                }
            }
        }
    }


}

@Composable
@Preview
private fun ProductOverviewScreenPreview() {
    EShopTheme {
        ProductOverviewScreenContent(
            state = ProductOverviewState(isPopularProductsLoading = true, isAllProductsLoading = true),
            onEvent = {},
            onNavigate = {},
            focusRequester = FocusRequester(),
            scaffoldState = rememberScaffoldState()
        )
    }
}
