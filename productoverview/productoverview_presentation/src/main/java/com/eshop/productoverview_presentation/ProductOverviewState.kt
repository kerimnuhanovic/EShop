package com.eshop.productoverview_presentation

data class ProductOverviewState(
    val searchQuery: String = "",
    val isSearchBarExpanded: Boolean = false
)
