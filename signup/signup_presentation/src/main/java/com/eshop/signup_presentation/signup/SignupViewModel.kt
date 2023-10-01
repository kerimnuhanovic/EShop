package com.eshop.signup_presentation.signup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(): ViewModel() {
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
                println("EVO ME U VIEW MODELU: ${_state.value}")
            }
        }
    }

}