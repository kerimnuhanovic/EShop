package com.eshop.login_presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshop.core.domain.preferences.Preferences
import com.eshop.core.navigation.Route
import com.eshop.login_domain.models.Credentials
import com.eshop.login_domain.usecase.LoginUseCase
import com.eshop.login_presentation.login.components.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.eshop.core.util.Result
import com.eshop.coreui.util.UiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val preferences: Preferences
) : ViewModel() {

    private val identifier: MutableStateFlow<String> = MutableStateFlow("")
    private val password: MutableStateFlow<String> = MutableStateFlow("")
    private val isPasswordVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val errorMessageId: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val loginState = combine(identifier, password, isPasswordVisible, errorMessageId, isLoading) { identifier, password, isPasswordVisible, errorMessageId, isLoading ->
        LoginState(
            identifier,
            password,
            isPasswordVisible,
            errorMessageId,
            isLoading
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), LoginState())

    fun onIdentifierEnter(identifier: String) {
        this.identifier.value = identifier
    }

    fun onPasswordEnter(password: String) {
        this.password.value = password
    }

    fun onPasswordVisibilityIconClick() {
        isPasswordVisible.value = !isPasswordVisible.value
    }

    fun onLoginClick() {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = loginUseCase(Credentials(identifier.value, password.value))) {
                is Result.Success -> {
                    preferences.saveToken(result.data.token)
                    _uiEvent.send(UiEvent.Navigate(Route.MAIN))
                }
                is Result.Failure -> {
                    errorMessageId.value = result.errorMessageId
                }
            }
            isLoading.value = false
        }
    }

}
