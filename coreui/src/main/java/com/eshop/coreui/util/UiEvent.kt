package com.eshop.coreui.util

sealed interface UiEvent {
    data class Navigate(val route: String): UiEvent
}
