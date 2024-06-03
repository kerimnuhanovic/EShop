package com.eshop.core.domain.models

import java.time.LocalDate

data class FavouriteShop(val id: String, val userId: String, val shopId: String, val dateCreated: LocalDate)
