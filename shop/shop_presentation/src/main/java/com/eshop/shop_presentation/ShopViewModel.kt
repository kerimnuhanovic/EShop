package com.eshop.shop_presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshop.core.domain.models.Product
import com.eshop.core.util.Result
import com.eshop.coreui.util.UiEvent
import com.eshop.shop_domain.model.AllReviews
import com.eshop.shop_domain.usecase.FetchShopProducts
import com.eshop.shop_domain.usecase.FetchShopReviewsUseCase
import com.eshop.shop_domain.usecase.FetchShopUseCase
import com.eshop.shop_presentation.util.ShopLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchShopUseCase: FetchShopUseCase,
    private val fetchShopProducts: FetchShopProducts,
    private val fetchShopReviewsUseCase: FetchShopReviewsUseCase
) : ViewModel() {
    private val shopId: String = checkNotNull(savedStateHandle["shopId"])

    private val _state: MutableStateFlow<ShopState> = MutableStateFlow(ShopState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        fetchShopData()
    }


    fun onEvent(event: ShopEvent) {
        when (event) {
            ShopEvent.OnBackClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateBack)
                }
            }

            ShopEvent.OnLocationSectionClick -> {
                _state.value = _state.value.copy(
                    isLocationSectionExpanded = !state.value.isLocationSectionExpanded
                )
            }

            is ShopEvent.OnShopLayoutChange -> {
                _state.value = _state.value.copy(
                    selectedLayout = ShopLayout.fromString(event.layout)
                )
            }
        }
    }

    private fun fetchShopData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isShopLoading = true
            )
            when (val result = fetchShopUseCase(shopId)) {
                is Result.Success -> {
                    val fetchShopProductsJob = async {
                        fetchShopProducts(result.data.username)
                    }
                    val fetchShopReviewsJob = async {
                        fetchShopReviewsUseCase(result.data.username)
                    }
                    val productsResult = fetchShopProductsJob.await()
                    val reviewsResult = fetchShopReviewsJob.await()
                    when {
                        productsResult is Result.Failure || reviewsResult is Result.Failure -> {
                            // display error layout
                            println("Evo me u failure shop screen")
                        }
                        else -> {
                            val products = productsResult as Result.Success<List<Product>>
                            val allReviews = reviewsResult as Result.Success<AllReviews>
                            _state.value = _state.value.copy(
                                shop = result.data,
                                isShopLoading = false,
                                products = products.data,
                                reviews = allReviews.data.reviews,
                                rating = allReviews.data.rating
                            )
                            println("EVO MEEEE")
                            println(allReviews.data.reviews)
                        }
                    }
                }
                is Result.Failure -> {
                    println("Evo me u failure shop screen")
                    // show failure message
                }
            }
        }
    }

}