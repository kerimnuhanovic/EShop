package com.eshop.productoverview_presentation

import android.net.Uri
import com.eshop.coreui.util.ShopAndProductCategory
import com.eshop.core.domain.models.Product

data class ProductOverviewState(
    val popularProducts: List<Product> = emptyList(),
    val allProducts: List<Product> = emptyList(),
    val isPopularProductsLoading: Boolean = false,
    val isAllProductsLoading: Boolean = false,
    val searchQuery: String = "",
    val isSearchBarExpanded: Boolean = false,
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
    val areAllProductsLoaded: Boolean = false
)
