package com.eshop.favouriteproducts_presentation

sealed interface FavouriteProductsEvent {
    object OnNavigateBack : FavouriteProductsEvent
    data class OnProductClick(val productId: String) : FavouriteProductsEvent
}