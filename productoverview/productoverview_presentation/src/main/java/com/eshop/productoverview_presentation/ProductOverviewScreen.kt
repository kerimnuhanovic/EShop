package com.eshop.productoverview_presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.eshop.coreui.components.ProductCard
import com.eshop.coreui.components.SearchBar
import com.eshop.coreui.components.TopBanner
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.util.ShopAndProductCategory
import com.eshop.coreui.util.UiEvent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductOverviewScreen(
    viewModel: ProductOverviewViewModel = hiltViewModel(),
    modalBottomSheetState: ModalBottomSheetState,
    onNavigate: (UiEvent.Navigate) -> Unit,
) {
    val state = viewModel.state.collectAsState().value
    ProductOverviewScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        modalBottomSheetState = modalBottomSheetState
    )

}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
private fun ProductOverviewScreenContent(
    state: ProductOverviewState,
    onEvent: (ProductOverviewEvent) -> Unit,
    modalBottomSheetState: ModalBottomSheetState,
) {
    val dimensions = LocalDimensions.current
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uriList ->
            uriList.map {
                onEvent(ProductOverviewEvent.OnProductPhotosSelect(uriList))
            }
        }
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
                        .height(dimensions.buttonHeight_50),
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
        ) {
            TopBanner(
                iconId = R.drawable.eshoplogo,
                titleId = R.string.eshop,
                subtitleId = R.string.your_online_shop_destination
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensions.spaceMedium)
            ) {
                SearchBar(
                    inputText = state.searchQuery,
                    onTextChange = {
                        onEvent(ProductOverviewEvent.OnSearchQueryEnter(it))
                    },
                    onIconClick = { onEvent(ProductOverviewEvent.OnSearchIconClick) },
                    isSingleLine = true,
                    placeholderId = R.string.search,
                    isExpanded = state.isSearchBarExpanded
                )
                Spacer(modifier = Modifier.height(dimensions.spaceLarge))
                FlowRow {
                    ProductCard(
                        image = "https://m.media-amazon.com/images/I/71vFKBpKakL._AC_UF1000,1000_QL80_.jpg",
                        productName = "Samsung Galaxy S23 Samsung Galaxy S23",
                        price = 20.0,
                        date = "20.9.2023",
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(dimensions.uploadImageSurfaceSize)
                            .padding(
                                top = dimensions.spaceSmall,
                                bottom = dimensions.spaceSmall,
                                end = dimensions.spaceSmall
                            )
                    )
                    ProductCard(
                        image = "https://m.media-amazon.com/images/I/71vFKBpKakL._AC_UF1000,1000_QL80_.jpg",
                        productName = "Samsung Galaxy S23",
                        price = 20.0,
                        date = "20.9.2023",
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(dimensions.uploadImageSurfaceSize)
                            .padding(
                                top = dimensions.spaceSmall,
                                bottom = dimensions.spaceSmall,
                                start = dimensions.spaceSmall
                            )
                    )
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
            state = ProductOverviewState(),
            onEvent = {},
            modalBottomSheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden
            )
        )
    }
}
