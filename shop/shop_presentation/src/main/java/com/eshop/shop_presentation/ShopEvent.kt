package com.eshop.shop_presentation

sealed interface ShopEvent {
    object OnBackClick : ShopEvent
    object OnLocationSectionClick : ShopEvent
    data class OnShopLayoutChange(val layout: String) : ShopEvent
    data class OnReviewEnter(val review: String) : ShopEvent
    data class OnStarClick(val rating: Int) : ShopEvent
    object OnReviewSubmit : ShopEvent
    object OnFavouriteClick : ShopEvent
}