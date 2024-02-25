package com.eshop.shop_domain.model

data class AllReviews(
    val rating: Double,
    val reviews: List<Review>
)
