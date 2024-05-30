package com.eshop.shop_presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshop.core.domain.models.Product
import com.eshop.core.domain.usecase.AddFavouriteShopUseCase
import com.eshop.core.domain.usecase.CheckIsShopFavouriteUseCase
import com.eshop.core.domain.usecase.DeleteFavouriteShopUseCase
import com.eshop.core.util.DELAY_1000
import com.eshop.core.util.REVIEW_MAX_CHARACTERS
import com.eshop.core.util.Result
import com.eshop.core.util.ToastMessage
import com.eshop.coreui.util.UiEvent
import com.eshop.shop_domain.model.AllReviews
import com.eshop.shop_domain.usecase.AddReviewUseCase
import com.eshop.shop_domain.usecase.FetchShopProducts
import com.eshop.shop_domain.usecase.FetchShopReviewsUseCase
import com.eshop.shop_domain.usecase.FetchShopUseCase
import com.eshop.shop_presentation.util.ShopLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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
    private val fetchShopReviewsUseCase: FetchShopReviewsUseCase,
    private val addReviewUseCase: AddReviewUseCase,
    private val addFavouriteShopUseCase: AddFavouriteShopUseCase,
    private val checkIsShopFavouriteUseCase: CheckIsShopFavouriteUseCase,
    private val deleteFavouriteShopUseCase: DeleteFavouriteShopUseCase
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

            is ShopEvent.OnReviewEnter -> {
                if (state.value.review.length < REVIEW_MAX_CHARACTERS) {
                    _state.value = _state.value.copy(
                        review = event.review
                    )
                }
            }

            is ShopEvent.OnStarClick -> {
                _state.value = _state.value.copy(
                    newRating = event.rating
                )
            }

            ShopEvent.OnReviewSubmit -> {
                addReview()
            }

            ShopEvent.OnFavouriteClick -> {
                changeFavouriteStatus()
            }
        }
    }

    private fun changeFavouriteStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val isProductFavourite = state.value.isShopFavourite
            _state.value = state.value.copy(
                isShopFavourite = !state.value.isShopFavourite
            )
            if (isProductFavourite) {
                when (deleteFavouriteShopUseCase(shopId)) {
                    is Result.Success -> {
                        _uiEvent.send(UiEvent.DisplayToast(ToastMessage.FavouriteShopDelete.message))
                    }

                    is Result.Failure -> {
                        _uiEvent.send(UiEvent.DisplayToast(ToastMessage.FavouriteShopDeleteFailed.message))
                        _state.value = state.value.copy(
                            isShopFavourite = !state.value.isShopFavourite
                        )
                    }
                }
            } else {
                when (addFavouriteShopUseCase(shopId)) {
                    is Result.Success -> {
                        _uiEvent.send(UiEvent.DisplayToast(ToastMessage.FavouriteShopAdded.message))
                    }

                    is Result.Failure -> {
                        _uiEvent.send(UiEvent.DisplayToast(ToastMessage.FavouriteShopAddFailed.message))
                        _state.value = state.value.copy(
                            isShopFavourite = !state.value.isShopFavourite
                        )
                    }
                }
            }
        }
    }

    private fun fetchShopData() {
        viewModelScope.launch {
            val fetchShopDataJob = viewModelScope.async {
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
                            productsResult is Result.Failure || reviewsResult is Result.Failure -> TODO()
                            else -> {
                                val products = productsResult as Result.Success<List<Product>>
                                val allReviews = reviewsResult as Result.Success<AllReviews>
                                return@async state.value.copy(
                                    shop = result.data,
                                    isShopLoading = false,
                                    products = products.data,
                                    reviews = allReviews.data.reviews,
                                    rating = allReviews.data.rating
                                )
                            }
                        }
                    }
                    is Result.Failure -> TODO()
                }
            }
            val isShopFavouriteJob = viewModelScope.async {
                when (val result = checkIsShopFavouriteUseCase(shopId)) {
                    is Result.Success -> {
                        return@async result.data
                    }

                    is Result.Failure -> TODO()
                }
            }
            val newState = fetchShopDataJob.await()
            val isProductFavourite = isShopFavouriteJob.await()
            _state.value = newState.copy(
                isShopFavourite = isProductFavourite
            )
        }
    }

    private fun addReview() {
        _state.value = state.value.copy(
            isReviewSubmitting = true
        )
        viewModelScope.launch {
            when (val result = addReviewUseCase(shopId = state.value.shop!!.username, comment = state.value.review, rating = state.value.newRating!!)) {
                is Result.Success -> {
                    val reviews = state.value.reviews.toMutableList()
                    reviews.add(0, result.data)
                    _state.value = state.value.copy(
                        isReviewSubmitting = false,
                        reviews = reviews
                    )
                    _uiEvent.send(UiEvent.CloseBottomSheet)
                    delay(DELAY_1000)
                    _uiEvent.send(UiEvent.DisplayToast(ToastMessage.ReviewSubmitted.message))
                }
                is Result.Failure -> {
                    _state.value = state.value.copy(
                        isReviewSubmitting = false
                    )
                    _uiEvent.send(UiEvent.DisplayToast(ToastMessage.ReviewNotSubmitted.message))
                }
            }
        }
    }

}