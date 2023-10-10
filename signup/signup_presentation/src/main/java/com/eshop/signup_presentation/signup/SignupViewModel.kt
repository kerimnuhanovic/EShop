package com.eshop.signup_presentation.signup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor() : ViewModel() {
    private val _state: MutableStateFlow<SignupState> = MutableStateFlow(SignupState())
    val state = _state.asStateFlow()

    fun onEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.OnNameEnter -> {
                _state.value = _state.value.copy(
                    name = event.name
                )
            }
            is SignupEvent.OnSurnameEnter -> {
                _state.value = _state.value.copy(
                    surname = event.surname
                )
            }
            is SignupEvent.OnUsernameEnter -> {
                _state.value = _state.value.copy(
                    username = event.username
                )
            }
            is SignupEvent.OnEmailEnter -> {
                _state.value = _state.value.copy(
                    email = event.email
                )
            }
            is SignupEvent.OnPasswordEnter -> {
                _state.value = _state.value.copy(
                    password = event.password
                )
            }
            is SignupEvent.OnConfirmPasswordEnter -> {
                _state.value = _state.value.copy(
                    confirmPassword = event.confirmPassword
                )
            }
            is SignupEvent.OnUserTypeSelect -> {

            }
            SignupEvent.OnPasswordVisibilityIconClick -> {
                _state.value = _state.value.copy(
                    isPasswordVisible = !state.value.isPasswordVisible
                )
            }
            SignupEvent.OnConfirmPasswordVisibilityIconClick -> {
                _state.value = _state.value.copy(
                    isConfirmPasswordVisible = !state.value.isConfirmPasswordVisible
                )
            }
            is SignupEvent.OnImageSelect -> {
                _state.value = _state.value.copy(
                    profileImage = event.image
                )
            }
            is SignupEvent.OnUserRoleSelect -> {
                _state.value = _state.value.copy(
                    userRole = event.userRole
                )
            }
            SignupEvent.OnExpandChange -> {
                _state.value = _state.value.copy(
                    isCategoryDropdownMenuExpanded = !state.value.isCategoryDropdownMenuExpanded
                )
            }
            is SignupEvent.OnShopCategoryClick -> {
                if (!_state.value.listOfShopCategories.contains(event.shopCategory)) {
                    _state.value = _state.value.copy(
                        listOfShopCategories = _state.value.listOfShopCategories.plus(event.shopCategory)
                    )
                } else {
                    _state.value = _state.value.copy(
                        listOfShopCategories = _state.value.listOfShopCategories.filterNot {
                            it == event.shopCategory
                        }
                    )
                }
            }
            is SignupEvent.OnShopLocationAdd -> {
                if (!_state.value.listOfShopLocations.contains(event.shopLocation) && event.shopLocation.value != "") {
                    _state.value = _state.value.copy(
                        listOfShopLocations = _state.value.listOfShopLocations.plus(event.shopLocation),
                        shopLocation = ""
                    )
                }
            }
            is SignupEvent.OnShopLocationRemove -> {
                _state.value = _state.value.copy(
                    listOfShopLocations = _state.value.listOfShopLocations.filterNot {
                        it == event.shopLocation
                    }
                )
            }
            is SignupEvent.OnShopLocationEnter -> {
                _state.value = _state.value.copy(
                    shopLocation = event.location
                )
            }
        }
    }

}