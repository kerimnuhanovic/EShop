package com.eshop.shopoverview_presentation

import com.eshop.core.domain.models.Shop
import com.eshop.coreui.util.BottomBarItem
import com.eshop.coreui.util.ShopAndProductCategory
import com.eshop.coreui.util.SelectedCategory
import com.eshop.coreui.util.SelectedSortCriterion
import com.eshop.coreui.util.generateSortCriteriaForShops

data class ShopOverviewState(
    val popularShops: List<Shop> = emptyList(),
    val searchQuery: String = "",
    val isPopularShopsLoading: Boolean = false,
    val isAllShopsLoading: Boolean = false,
    val allShops: List<Shop> = emptyList(),
    val areAllShopsLoaded: Boolean = false,
    val isLoadingMoreShops: Boolean = false,
    val isSearchBarVisible: Boolean = false,
    val areSearchedShopsDisplayed: Boolean = false,
    val searchedQuery: String = "",
    val isFilterDrawerItemExpanded: Boolean = false,
    val shopCategories: List<SelectedCategory> = ShopAndProductCategory.listAllCategories().map { SelectedCategory(it, false) },
    val isSortDrawerItemExpanded: Boolean = false,
    val sortCriteria: List<SelectedSortCriterion> = generateSortCriteriaForShops(),
    val bottomBarItems: List<BottomBarItem> = emptyList()
)
