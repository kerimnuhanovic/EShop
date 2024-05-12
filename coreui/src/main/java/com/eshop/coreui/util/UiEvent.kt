package com.eshop.coreui.util

sealed interface UiEvent {
    data class Navigate(val route: String): UiEvent
    data class ScrollPage(val page: Int): UiEvent
    object NavigateBack: UiEvent
    object ChangeNavigationDrawerState : UiEvent
    object FocusInputField : UiEvent
    data class DisplayToast(val message: String) : UiEvent
}
