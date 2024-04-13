package com.eshop.shopoverview_domain.repository

import com.eshop.core.domain.models.FilteredShopAndProductCategory
import com.eshop.core.domain.models.Shop
import com.eshop.core.util.Result

interface ShopOverviewRepository {
    suspend fun fetchAllShops(
        offset: Int,
        searchQuery: String?,
        filteredCategories: List<FilteredShopAndProductCategory>?,
        sortBy: String?,
        orderBy: String?
    ): Result<List<Shop>>

    suspend fun fetchPopularShops(): Result<List<Shop>>
}