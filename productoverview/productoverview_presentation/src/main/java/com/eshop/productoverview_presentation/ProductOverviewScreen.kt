package com.eshop.productoverview_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.R
import com.eshop.coreui.components.ProductCard
import com.eshop.coreui.components.SearchBar
import com.eshop.coreui.components.TopBanner
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.util.UiEvent

@Composable
fun ProductOverviewScreen(
    viewModel: ProductOverviewViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val state = viewModel.state.collectAsState().value
    ProductOverviewScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProductOverviewScreenContent(
    state: ProductOverviewState,
    onEvent: (ProductOverviewEvent) -> Unit
) {
    val dimensions = LocalDimensions.current
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

@Composable
@Preview
private fun ProductOverviewScreenPreview() {
    EShopTheme {
        ProductOverviewScreenContent(state = ProductOverviewState(), onEvent = {})
    }
}
