package com.eshop.shop_domain.repository

import com.eshop.core.domain.models.Product
import com.eshop.core.domain.models.Shop
import com.eshop.core.util.Result
import com.eshop.shop_domain.model.AllReviews
import com.eshop.shop_domain.model.Review

interface ShopRepository {
    suspend fun fetchShop(id: String): Result<Shop>
    suspend fun fetchShopProducts(shopId: String): Result<List<Product>>
    suspend fun fetchShopReviews(shopId: String): Result<AllReviews>
    suspend fun addReview(shopId: String, comment: String, rating: Int): Result<Review>
}