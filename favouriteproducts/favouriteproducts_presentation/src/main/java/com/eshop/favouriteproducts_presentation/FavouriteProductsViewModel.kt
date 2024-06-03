package com.eshop.favouriteproducts_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshop.core.domain.usecase.GetFavouriteProductsUseCase
import com.eshop.core.util.Result
import com.eshop.coreui.navigation.Route
import com.eshop.coreui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteProductsViewModel @Inject constructor(
    private val getFavouriteProductsUseCase: GetFavouriteProductsUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<FavouriteProductsState> = MutableStateFlow(
        FavouriteProductsState()
    )
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        fetchFavouriteProducts()
    }

    fun onEvent(event: FavouriteProductsEvent) {
        when (event) {
            FavouriteProductsEvent.OnNavigateBack -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateBack)
                }
            }

            is FavouriteProductsEvent.OnProductClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate("${Route.PRODUCT}/${event.productId}"))
                }
            }
        }
    }

    private fun fetchFavouriteProducts() {
        _state.value = state.value.copy(
            isLoading = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getFavouriteProductsUseCase()) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        products = result.data
                    )
                }
                is Result.Failure -> TODO()
            }
        }
    }
}