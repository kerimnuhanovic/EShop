package com.eshop.shop_data.remote.dto

data class ReviewDto(
    val id: String,
    val shopId: String,
    val authorId: String,
    val authorProfileImage: String,
    val rating: Double,
    val comment: String,
    val dateAdded: String
)

data class AllReviewsDto(
    val rating: Double,
    val data: List<ReviewDto>
)
