package com.eshop.shopoverview_presentation

sealed interface ShopOverviewEvent {
    data class OnSearchQueryEnter(val searchQuery: String) : ShopOverviewEvent
    object OnSearchIconClick : ShopOverviewEvent
    data class OnShopClick(val shopId: String) : ShopOverviewEvent
    object OnScreenEndReach : ShopOverviewEvent
}