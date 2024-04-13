package com.eshop.product_presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.eshop.core.util.BASE_URL
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.R
import com.eshop.coreui.components.EShopButton
import com.eshop.coreui.components.PageIndicator
import com.eshop.coreui.theme.LightGray
import com.eshop.coreui.theme.ShimmerLightColor
import com.eshop.coreui.util.UiEvent
import com.eshop.coreui.util.loadingAnimation
import java.text.NumberFormat
import java.util.Currency

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value
    val pagerState = rememberPagerState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            if (uiEvent is UiEvent.Navigate) {
                onNavigate(uiEvent)
            } else if (uiEvent == UiEvent.NavigateBack) {
                onNavigateBack()
            }
        }
    }
    ProductScreenContent(state = state, onEvent = viewModel::onEvent, pagerState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProductScreenContent(
    state: ProductState,
    onEvent: (ProductEvent) -> Unit,
    pagerState: PagerState,
) {
    val dimensions = LocalDimensions.current
    val currencyFormat = remember {
        NumberFormat.getCurrencyInstance()
    }
    currencyFormat.currency = Currency.getInstance("EUR")
    val verticalGradient = Brush.verticalGradient(
        listOf(Color.Black.copy(alpha = 0.3f), Color.Transparent),
        tileMode = TileMode.Clamp
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (state.isLoading) {
                    Box(
                        modifier = Modifier
                            .height(250.dp)
                            .fillMaxWidth()
                            .loadingAnimation(
                                isLoading = true,
                                shimmerColor = MaterialTheme.colors.onSecondary
                            )
                    ) {

                    }
                }
                if (!state.isLoading && state.product != null) {
                    HorizontalPager(
                        state = pagerState,
                        pageCount = state.product.images.size
                    ) { page ->
                        Box {
                            AsyncImage(
                                model = "$BASE_URL/${state.product.images[page]}",
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp),
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
                    PageIndicator(
                        currentPage = pagerState.currentPage,
                        numberOfPages = state.product.images.size,
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
                        onEvent(ProductEvent.OnBackClick)
                    },
                    tint = Color.White
                )
                if (state.product != null) {
                    Icon(
                        imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(if (state.isLoading) dimensions.size_52 else dimensions.spaceMedium))
        Box(
            modifier = Modifier
                .defaultMinSize(minWidth = dimensions.size_200, minHeight = dimensions.size_24)
                .padding(horizontal = dimensions.spaceMedium)
                .loadingAnimation(
                    isLoading = state.isLoading,
                    shimmerColor = MaterialTheme.colors.onSecondary
                )
        ) {
            if (!state.isLoading && state.product != null) {
                Text(
                    text = state.product.title,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dimensions.font_24,
                    color = Color.Black
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(
                    start = dimensions.spaceMedium,
                    end = dimensions.spaceMedium,
                    top = if (state.isLoading) dimensions.spaceSmall else dimensions.default
                )
                .fillMaxWidth()
                .defaultMinSize(minHeight = dimensions.size_20)
                .loadingAnimation(
                    isLoading = state.isLoading,
                    shimmerColor = MaterialTheme.colors.onSecondary
                )
        ) {
            if (!state.isLoading && state.productOwner != null) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.height(dimensions.size_20)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.width(dimensions.spaceSmall))
                        Text(
                            text = state.productOwner.username,
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = dimensions.font_12,
                            color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                        )
                    }
                    Spacer(modifier = Modifier.height(dimensions.spaceSmall))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.height(dimensions.size_20)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.width(dimensions.spaceSmall))
                        Text(
                            text = state.productOwner.shopLocations.first(),
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = dimensions.font_12,
                            color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(horizontal = dimensions.spaceMedium, vertical = dimensions.spaceLarge)
                .fillMaxWidth()
                .defaultMinSize(minHeight = dimensions.size_200)
                .loadingAnimation(
                    isLoading = state.isLoading,
                    shimmerColor = MaterialTheme.colors.onSecondary
                )
        ) {
            if (!state.isLoading && state.product != null) {
                Text(
                    text = state.product.description, fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = dimensions.font_14,
                    color = Color.Black
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(horizontal = dimensions.spaceMedium)
                .defaultMinSize(minHeight = dimensions.size_20, minWidth = dimensions.size_200)
                .loadingAnimation(
                    isLoading = state.isLoading,
                    shimmerColor = MaterialTheme.colors.onSecondary
                )
        ) {
            if (!state.isLoading && state.product != null) {
                Text(
                    text = currencyFormat.format(state.product.price),
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dimensions.font_20,
                    color = Color.Black,
                    textAlign = TextAlign.End
                )
            }
        }
        if (!state.isLoading) {
            EShopButton(
                content = {
                    if (!state.isAddingProductInProgress) {
                        Text(
                            text = stringResource(id = R.string.add_to_cart),
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
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                onButtonClick = { onEvent(ProductEvent.OnAddToCartClick(state.product!!.id)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensions.spaceMedium,
                        end = dimensions.spaceMedium,
                        bottom = dimensions.spaceMedium,
                        top = dimensions.spaceLarge
                    ),
                shape = RectangleShape
            )
        }
    }
}