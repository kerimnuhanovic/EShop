package com.eshop.productoverview_presentation

import androidx.lifecycle.ViewModel
import com.eshop.coreui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class ProductOverviewViewModel @Inject constructor(

): ViewModel() {

    private val _state: MutableStateFlow<ProductOverviewState> = MutableStateFlow(ProductOverviewState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ProductOverviewEvent) {
        when (event) {
            ProductOverviewEvent.OnSearchIconClick -> {
                _state.value = _state.value.copy(
                    isSearchBarExpanded = !state.value.isSearchBarExpanded
                )
            }
            is ProductOverviewEvent.OnSearchQueryEnter -> {
                _state.value = _state.value.copy(
                    searchQuery = event.query
                )
            }
        }
    }

}