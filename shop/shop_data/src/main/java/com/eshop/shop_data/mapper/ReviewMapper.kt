package com.eshop.shop_data.mapper

import com.eshop.shop_data.remote.dto.AllReviewsDto
import com.eshop.shop_data.remote.dto.ReviewDto
import com.eshop.shop_domain.model.AllReviews
import com.eshop.shop_domain.model.Review
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun ReviewDto.toReview(): Review {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val dateReviewIsAdded = LocalDate.parse(this.dateAdded, inputFormatter)
    return Review(
        id = this.id,
        shopId = this.shopId,
        authorUsername = this.authorId,
        authorProfileImage = this.authorProfileImage,
        comment = this.comment,
        rating = this.rating,
        dateAdded = dateReviewIsAdded
    )
}

fun AllReviewsDto.toAllReviews(): AllReviews {
    return AllReviews(
        rating = this.rating,
        reviews = this.data.map { reviewDto ->
            reviewDto.toReview()
        }
    )
}