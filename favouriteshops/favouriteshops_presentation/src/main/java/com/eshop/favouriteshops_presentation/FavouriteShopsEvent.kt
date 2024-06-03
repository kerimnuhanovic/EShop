package com.eshop.favouriteshops_presentation

sealed interface FavouriteShopsEvent {
    object OnNavigateBack : FavouriteShopsEvent
    data class OnShopClick(val shopId: String) : FavouriteShopsEvent
}