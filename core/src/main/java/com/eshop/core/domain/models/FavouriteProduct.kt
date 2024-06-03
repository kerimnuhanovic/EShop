package com.eshop.core.domain.models

import java.time.LocalDate

data class FavouriteProduct(val id: String, val userId: String, val productId: String, val dateCreated: LocalDate)
