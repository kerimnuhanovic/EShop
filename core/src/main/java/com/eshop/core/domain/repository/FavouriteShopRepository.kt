package com.eshop.core.domain.repository

import com.eshop.core.domain.models.FavouriteShop
import com.eshop.core.domain.models.Shop
import com.eshop.core.util.Result

interface FavouriteShopRepository {
    suspend fun addShop(shopId: String): Result<FavouriteShop>
    suspend fun listShops(): Result<List<Shop>>
    suspend fun deleteShop(shopId: String): Result<Unit>
    suspend fun checkIsShopFavourite(shopId: String): Result<Boolean>
}