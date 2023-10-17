package com.eshop.productoverview_presentation

sealed interface ProductOverviewEvent {
    object OnSearchIconClick : ProductOverviewEvent
    data class OnSearchQueryEnter(val query: String) : ProductOverviewEvent
}
