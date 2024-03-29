package com.eshop.productoverview_presentation

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import com.eshop.coreui.components.EShopButton
import com.eshop.coreui.components.EShopDropdownMenu
import com.eshop.coreui.components.ErrorBox
import com.eshop.coreui.components.ImageHolder
import com.eshop.coreui.components.ImageUploadPlaceholder
import com.eshop.coreui.components.InputField
import com.eshop.coreui.components.ItemDataBox
import com.eshop.coreui.components.PrimarySearchBar
import com.eshop.coreui.components.ProductCard
import com.eshop.coreui.components.ProductCardPlaceholder
import com.eshop.coreui.components.ProductCardPlaceholderFlowRow
import com.eshop.coreui.components.ProductCardPlaceholderRow
import com.eshop.coreui.components.SearchBar
import com.eshop.coreui.components.TopBanner
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.util.ShopAndProductCategory
import com.eshop.coreui.util.UiEvent
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductOverviewScreen(
    viewModel: ProductOverviewViewModel = hiltViewModel(),
    modalBottomSheetState: ModalBottomSheetState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    topBarOffset: Float
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate -> onNavigate(uiEvent)
                is UiEvent.ScrollPage -> {
                    // No-op
                }
                UiEvent.NavigateBack -> {
                    // No-op
                }
            }
        }
    }
    ProductOverviewScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        modalBottomSheetState = modalBottomSheetState,
        topBarOffset = topBarOffset
    )

}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
private fun ProductOverviewScreenContent(
    state: ProductOverviewState,
    onEvent: (ProductOverviewEvent) -> Unit,
    modalBottomSheetState: ModalBottomSheetState,
    topBarOffset: Float
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
    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(top = dimensions.size_56)
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
                    text = stringResource(id = com.eshop.productoverview_presentation.R.string.all_products),
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
            Box(modifier = Modifier.offset { IntOffset(x = 0, y = topBarOffset.roundToInt()) }) {
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
                    })
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
private fun ProductOverviewScreenPreview() {
    EShopTheme {
        ProductOverviewScreenContent(
            state = ProductOverviewState(isPopularProductsLoading = true, isAllProductsLoading = true),
            onEvent = {},
            modalBottomSheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden
            ),
            0f
        )
    }
}
