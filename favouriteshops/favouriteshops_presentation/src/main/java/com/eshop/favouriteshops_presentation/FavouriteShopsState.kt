package com.eshop.favouriteshops_presentation

import com.eshop.core.domain.models.Shop

data class FavouriteShopsState(
    val isLoading: Boolean = false,
    val shops: List<Shop> = emptyList()
)
