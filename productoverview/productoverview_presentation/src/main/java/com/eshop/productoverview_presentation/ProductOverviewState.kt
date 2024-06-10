package com.eshop.productoverview_presentation

import android.net.Uri
import com.eshop.core.domain.models.Product
import com.eshop.coreui.util.BottomBarItem
import com.eshop.coreui.util.SelectedCategory
import com.eshop.coreui.util.SelectedSortCriterion
import com.eshop.coreui.util.ShopAndProductCategory
import com.eshop.coreui.util.generateSortCriteriaForProducts

data class ProductOverviewState(
    val popularProducts: List<Product> = emptyList(),
    val allProducts: List<Product> = emptyList(),
    val isPopularProductsLoading: Boolean = false,
    val isAllProductsLoading: Boolean = false,
    val searchQuery: String = "",
    val productTitle: String = "",
    val productDescription: String = "",
    val productCategory: String = "",
    val productPrice: String = "",
    val productImages: List<Uri> = listOf(),
    val isCategoryDropdownMenuExpanded: Boolean = false,
    val listOfProductCategories: List<ShopAndProductCategory> = emptyList(),
    val isProductAdditionInProgress: Boolean = false,
    val errorMessageId: Int? = null,
    val isLoadingMoreProducts: Boolean = false,
    val areAllProductsLoaded: Boolean = false,
    val isSearchBarVisible: Boolean = false,
    val areSearchedSProductsDisplayed: Boolean = false,
    val searchedQuery: String = "",
    val isFilterDrawerItemExpanded: Boolean = false,
    val productCategories: List<SelectedCategory> = ShopAndProductCategory.listAllCategories().map { SelectedCategory(it, false) },
    val isSortDrawerItemExpanded: Boolean = false,
    val sortCriteria: List<SelectedSortCriterion> = generateSortCriteriaForProducts(),
    val bottomBarItems: List<BottomBarItem> = emptyList()
)
