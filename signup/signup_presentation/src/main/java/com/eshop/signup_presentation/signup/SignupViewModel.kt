package com.eshop.signup_presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshop.core.domain.preferences.Preferences
import com.eshop.core.navigation.Route
import com.eshop.signup_domain.models.RegistrationData
import com.eshop.signup_domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.eshop.core.util.Result
import com.eshop.coreui.util.UiEvent
import com.eshop.core.domain.usecase.ConvertListToStringUseCase
import com.eshop.core.domain.usecase.CreateFileFromUriUseCase
import com.eshop.signup_domain.usecase.SignupInputVerificationUseCase
import com.eshop.signup_presentation.signup.util.FIRST_PAGE
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val createFileFromUriUseCase: CreateFileFromUriUseCase,
    private val convertListToStringUseCase: ConvertListToStringUseCase,
    private val signupInputVerificationUseCase: SignupInputVerificationUseCase,
    private val preferences: Preferences
) : ViewModel() {

    private val _state: MutableStateFlow<SignupState> = MutableStateFlow(SignupState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

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
            SignupEvent.OnRegisterClick -> {
                registerUser()
            }
        }
    }

    private fun registerUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val isUserInputValid = signupInputVerificationUseCase(
                state.value.name,
                state.value.surname,
                state.value.username,
                state.value.email,
                state.value.password,
                state.value.confirmPassword,
                state.value.userRole.role,
                state.value.profileImage,
                state.value.listOfShopCategories.map { it.value }
            )
            if (!isUserInputValid.isInputValid) {
                _state.value = _state.value.copy(
                    errorMessageId = isUserInputValid.errorMessageId
                )
                _uiEvent.send(UiEvent.ScrollPage(FIRST_PAGE))
                return@launch
            }
            _state.value = _state.value.copy(
                isLoading = true
            )
            val result = registerUseCase(
                RegistrationData(
                    name = state.value.name,
                    surname = state.value.surname,
                    username = state.value.username,
                    email = state.value.email,
                    password = state.value.password,
                    userType = state.value.userRole.role,
                    shopCategories = convertListToStringUseCase(state.value.listOfShopCategories.map { it.value }),
                    shopLocations = convertListToStringUseCase(state.value.listOfShopLocations.map { it.value }),
                    profileImage = createFileFromUriUseCase(state.value.profileImage)
                )
            )
            when (result) {
                is Result.Success -> {
                    preferences.saveToken(result.data.token)
                    _uiEvent.send(UiEvent.Navigate(Route.PRODUCTS_OVERVIEW))
                }
                is Result.Failure -> {
                    _state.value = _state.value.copy(
                        errorMessageId = result.errorMessageId
                    )
                    _uiEvent.send(UiEvent.ScrollPage(FIRST_PAGE))
                }
            }
            _state.value = _state.value.copy(
                isLoading = false
            )
        }
    }

}