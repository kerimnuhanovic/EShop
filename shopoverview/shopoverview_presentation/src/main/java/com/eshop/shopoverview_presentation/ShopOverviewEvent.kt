package com.eshop.shopoverview_presentation

import com.eshop.coreui.util.SelectedCategory
import com.eshop.coreui.util.SelectedSortCriterion

sealed interface ShopOverviewEvent {
    data class OnSearchQueryEnter(val searchQuery: String) : ShopOverviewEvent
    object OnSearchIconClick : ShopOverviewEvent
    object OnSearch : ShopOverviewEvent
    data class OnShopClick(val shopId: String) : ShopOverviewEvent
    object OnScreenEndReach : ShopOverviewEvent
    object OnExitSearchBarClick : ShopOverviewEvent
    object OnDeleteSearchTextClick : ShopOverviewEvent
    object OnFilterIconClick : ShopOverviewEvent
    object OnFilterDrawerItemClick : ShopOverviewEvent
    object OnFilterClick : ShopOverviewEvent
    object OnSortDrawerItemClick : ShopOverviewEvent
    data class OnShopCategorySelect(val selectedCategory: SelectedCategory) : ShopOverviewEvent
    data class OnSortCriterionSelect(val sortCriterion: SelectedSortCriterion) : ShopOverviewEvent
}