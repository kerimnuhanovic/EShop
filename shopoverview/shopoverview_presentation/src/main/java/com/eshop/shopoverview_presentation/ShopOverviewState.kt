package com.eshop.shopoverview_presentation

import com.eshop.core.domain.models.Shop

data class ShopOverviewState(
    val popularShops: List<Shop> = emptyList(),
    val searchQuery: String = "",
    val isSearchBarExpanded: Boolean = false,
    val isPopularShopsLoading: Boolean = false,
    val isAllShopsLoading: Boolean = false,
    val allShops: List<Shop> = emptyList(),
    val areAllShopsLoaded: Boolean = false,
    val isLoadingMoreShops: Boolean = false
)
