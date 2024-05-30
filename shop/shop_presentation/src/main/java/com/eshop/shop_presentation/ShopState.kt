package com.eshop.shop_presentation

import com.eshop.core.domain.models.Product
import com.eshop.core.domain.models.Shop
import com.eshop.shop_domain.model.Review
import com.eshop.shop_presentation.util.ShopLayout

data class ShopState(
    val shop: Shop? = null,
    val isShopLoading: Boolean = false,
    val isLocationSectionExpanded: Boolean = false,
    val selectedLayout: ShopLayout = ShopLayout.Products,
    val products: List<Product> = emptyList(),
    val reviews: List<Review> = emptyList(),
    val rating: Double = 0.0,
    val review: String = "",
    val newRating: Int? = null,
    val isReviewSubmitting: Boolean = false,
    val isShopFavourite: Boolean = false
)
