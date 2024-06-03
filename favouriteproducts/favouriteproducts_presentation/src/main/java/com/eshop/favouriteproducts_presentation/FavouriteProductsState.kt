package com.eshop.favouriteproducts_presentation

import com.eshop.core.domain.models.Product

data class FavouriteProductsState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList()
)
