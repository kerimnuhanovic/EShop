package com.eshop.shopoverview_presentation

sealed interface ShopOverviewEvent {
    data class OnSearchQueryEnter(val searchQuery: String) : ShopOverviewEvent
    object OnSearchIconClick : ShopOverviewEvent
    object OnSearch : ShopOverviewEvent
    data class OnShopClick(val shopId: String) : ShopOverviewEvent
    object OnScreenEndReach : ShopOverviewEvent
    object OnExitSearchBarClick : ShopOverviewEvent
    object OnDeleteSearchTextClick : ShopOverviewEvent
    object OnFilterIconClick : ShopOverviewEvent
}