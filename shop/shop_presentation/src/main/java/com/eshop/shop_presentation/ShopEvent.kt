package com.eshop.shop_presentation

sealed interface ShopEvent {
    object OnBackClick : ShopEvent
    object OnLocationSectionClick : ShopEvent
    data class OnShopLayoutChange(val layout: String) : ShopEvent
}