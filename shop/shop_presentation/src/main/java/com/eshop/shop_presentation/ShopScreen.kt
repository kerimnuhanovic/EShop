package com.eshop.shop_presentation

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eshop.core.util.REVIEW_MAX_CHARACTERS
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.components.EShopButton
import com.eshop.coreui.components.FloatingButton
import com.eshop.coreui.components.TextArea
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.theme.StarGreenVariant
import com.eshop.coreui.util.UiEvent
import com.eshop.shop_presentation.components.CategoryWidget
import com.eshop.shop_presentation.components.ImageCatalog
import com.eshop.shop_presentation.components.ProductItem
import com.eshop.shop_presentation.components.ReviewItem
import com.eshop.shop_presentation.components.ShopScreenLoadingSkeleton
import com.eshop.shop_presentation.components.StarsRating
import com.eshop.shop_presentation.components.TwoOptionSelector
import com.eshop.shop_presentation.util.ShopLayout

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ShopScreen(
    viewModel: ShopViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value
    val pagerState = rememberPagerState()

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

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            if (event is UiEvent.Navigate) {
                onNavigate(event)
            }
            if (event == UiEvent.NavigateBack) {
                onNavigateBack()
            }
            if (event == UiEvent.CloseBottomSheet) {
                bottomSheetState.hide()
            }
            if (event is UiEvent.DisplayToast) {
                Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
            }
        }
    }
    ShopScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        pagerState = pagerState,
        bottomSheetState = bottomSheetState,
        isBottomBarOverlapped = isBottomBarOverlapped
    )
}

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalLayoutApi::class,
    ExperimentalMaterialApi::class
)
@Composable
private fun ShopScreenContent(
    state: ShopState,
    onEvent: (ShopEvent) -> Unit,
    pagerState: PagerState,
    bottomSheetState: ModalBottomSheetState,
    isBottomBarOverlapped: MutableState<Boolean>
) {
    val dimensions = LocalDimensions.current
    val screenWidth = (LocalConfiguration.current.screenWidthDp).dp
    val animateOffsetForProducts = animateDpAsState(
        targetValue = if (state.selectedLayout == ShopLayout.Products) dimensions.default else -screenWidth,
        animationSpec = tween(1000), label = "productsOffset"
    )
    val animateOffsetForReviews = animateDpAsState(
        targetValue = if (state.selectedLayout == ShopLayout.Products) screenWidth else dimensions.default,
        animationSpec = tween(1000), label = "reviewsOffset"
    )

    if (state.isShopLoading) {
        ShopScreenLoadingSkeleton()
    } else if (state.shop != null) {
        Scaffold(
            floatingActionButton = {
                AnimatedVisibility(
                    visible = animateOffsetForReviews.value == dimensions.default,
                    enter = fadeIn(tween(100)),
                    exit = fadeOut(tween(100))
                ) {
                    FloatingButton(
                        modalBottomSheetState = bottomSheetState,
                        isBottomBarOverlapped = isBottomBarOverlapped
                    )
                }
            }
        ) {
            ModalBottomSheetLayout(
                sheetState = bottomSheetState,
                sheetContent = {
                    Column(
                        modifier = Modifier
                            .padding(dimensions.spaceMedium)
                            .fillMaxHeight(0.9f)
                    ) {
                        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
                        Text(
                            text = stringResource(id = R.string.add_review),
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = dimensions.font_20,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                        TextArea(inputText = state.review, onTextChange = {
                            onEvent(ShopEvent.OnReviewEnter(it))
                        }, placeholderId = R.string.start_typing)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.End)
                        ) {
                            Text(
                                text = "${state.review.length}/${REVIEW_MAX_CHARACTERS}",
                                fontFamily = PoppinsFontFamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = dimensions.font_14,
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .padding(top = dimensions.spaceExtraSmall)
                                    .fillMaxWidth()
                            )
                        }
                        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                        ) {
                            StarsRating(
                                rating = state.newRating ?: 0,
                                starSize = dimensions.size_40,
                                onStarClick = {
                                    onEvent(ShopEvent.OnStarClick(it))
                                })
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        EShopButton(
                            content = {
                                if (state.isReviewSubmitting) {
                                    CircularProgressIndicator(
                                        color = MaterialTheme.colors.onPrimary,
                                        modifier = Modifier.size(dimensions.size_32)
                                    )
                                } else {
                                    Text(
                                        text = stringResource(id = R.string.submit),
                                        fontFamily = PoppinsFontFamily,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = dimensions.font_16
                                    )
                                }
                            },
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = MaterialTheme.colors.onPrimary,
                            shape = RectangleShape,
                            onButtonClick = {
                                onEvent(ShopEvent.OnReviewSubmit)
                            },
                            enabled = !state.isReviewSubmitting && state.newRating != null && state.review.isNotEmpty(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = dimensions.spaceSmall)
                        )
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
                        .verticalScroll(rememberScrollState())
                        .padding(it)
                ) {
                    ImageCatalog(
                        pagerState = pagerState,
                        images = listOf(state.shop.profileImage),
                        onBackClick = {
                            onEvent(ShopEvent.OnBackClick)
                        },
                        showPageIndicator = false,
                        isFavourite = state.isShopFavourite,
                        onFavouriteClick = {
                            onEvent(ShopEvent.OnFavouriteClick)
                        }
                    )
                    Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                    Text(
                        text = state.shop.username,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = dimensions.font_24,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = dimensions.spaceMedium)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(dimensions.size_20)
                            .padding(horizontal = dimensions.spaceMedium)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = null,
                            tint = StarGreenVariant
                        )
                        Spacer(modifier = Modifier.width(dimensions.spaceExtraSmall))
                        Text(
                            text = "${state.rating}",
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = dimensions.font_12,
                            color = StarGreenVariant
                        )
                        Spacer(modifier = Modifier.width(dimensions.spaceSmall))
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.width(dimensions.spaceExtraSmall))
                        Text(
                            text = state.shop.shopLocations.first(),
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = dimensions.font_12,
                            color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                        )
                    }
                    Spacer(modifier = Modifier.height(dimensions.spaceSmall))
                    FlowRow(modifier = Modifier.padding(horizontal = dimensions.spaceMedium)) {
                        state.shop.shopCategories.forEach { category ->
                            CategoryWidget(text = category)
                        }
                    }
                    Spacer(modifier = Modifier.height(dimensions.spaceSmall))
                    Divider(
                        color = MaterialTheme.colors.onSecondary.copy(0.5f),
                        modifier = Modifier
                            .height(dimensions.size_1)
                            .fillMaxWidth()
                    )
                    Row(modifier = Modifier
                        .padding(vertical = dimensions.spaceExtraSmall)
                        .clickable { onEvent(ShopEvent.OnLocationSectionClick) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                            modifier = Modifier.padding(start = dimensions.spaceMedium)
                        )
                        Text(
                            text = stringResource(id = R.string.locations),
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = dimensions.font_16,
                            color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = if (!state.isLocationSectionExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.padding(end = dimensions.spaceMedium)
                        )
                    }
                    AnimatedVisibility(visible = state.isLocationSectionExpanded) {
                        FlowRow(
                            modifier = Modifier
                                .padding(horizontal = dimensions.spaceMedium)
                        ) {
                            state.shop.shopLocations.forEach { location ->
                                CategoryWidget(text = location)
                            }
                        }
                    }
                    Divider(
                        color = MaterialTheme.colors.onSecondary.copy(0.5f),
                        modifier = Modifier
                            .height(dimensions.size_1)
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(dimensions.spaceLarge))
                    TwoOptionSelector(
                        optionOne = ShopLayout.Products.value,
                        optionTwo = ShopLayout.Reviews.value,
                        selectedOption = state.selectedLayout.value,
                        onOptionChange = { layout ->
                            onEvent(ShopEvent.OnShopLayoutChange(layout))
                        },
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                    Box {
                        if (animateOffsetForProducts.value != -screenWidth) {
                            Column(
                                modifier = Modifier
                                    .offset(animateOffsetForProducts.value)
                            ) {
                                state.products.forEach { product ->
                                    ProductItem(
                                        product = product,
                                        modifier = Modifier.padding(
                                            horizontal = dimensions.spaceMedium,
                                            vertical = dimensions.spaceSmall
                                        )
                                    )
                                }
                                if (state.products.isEmpty()) {
                                    Text(
                                        text = stringResource(id = R.string.no_products),
                                        fontSize = dimensions.font_16,
                                        fontFamily = PoppinsFontFamily,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(horizontal = dimensions.spaceMedium)
                                    )
                                }
                            }
                        }
                        if (animateOffsetForReviews.value != screenWidth) {
                            Column(
                                modifier = Modifier
                                    .offset(animateOffsetForReviews.value)
                            ) {
                                state.reviews.forEach { review ->
                                    ReviewItem(
                                        review = review,
                                    )
                                    Divider(
                                        color = MaterialTheme.colors.onSecondary.copy(0.5f),
                                        modifier = Modifier
                                            .height(dimensions.size_1)
                                            .fillMaxWidth()
                                    )
                                }
                                if (state.reviews.isEmpty()) {
                                    Text(
                                        text = stringResource(id = R.string.no_reviews),
                                        fontSize = dimensions.font_16,
                                        fontFamily = PoppinsFontFamily,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(horizontal = dimensions.spaceMedium)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
@Preview
private fun ShopScreenPreview() {
    EShopTheme {
        ShopScreenContent(
            state = ShopState(),
            onEvent = {},
            rememberPagerState(),
            bottomSheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden
            ),
            isBottomBarOverlapped = remember {
                mutableStateOf(false)
            }
        )
    }
}