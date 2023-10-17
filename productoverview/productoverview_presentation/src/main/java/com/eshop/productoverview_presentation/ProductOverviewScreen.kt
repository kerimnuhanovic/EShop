package com.eshop.productoverview_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.R
import com.eshop.coreui.components.SearchBar
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
    }
}

@Composable
@Preview
private fun ProductOverviewScreenPreview() {
    EShopTheme {
        ProductOverviewScreenContent(state = ProductOverviewState(), onEvent = {})
    }
}
