package com.eshop.shop_domain.model

import java.time.LocalDate

data class Review(
    val id: String,
    val shopId: String,
    val authorUsername: String,
    val authorProfileImage: String,
    val comment: String,
    val rating: Double,
    val dateAdded: LocalDate
)
