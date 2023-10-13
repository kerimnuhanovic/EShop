package com.eshop.login_presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.R
import com.eshop.coreui.components.EShopButton
import com.eshop.coreui.components.ErrorBox
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.util.UiEvent
import com.eshop.coreui.components.InputField

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val state = viewModel.loginState.collectAsState().value
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate -> onNavigate(uiEvent)
                is UiEvent.ScrollPage -> {
                    // No-op
                }
            }
        }
    }
    LoginScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun LoginScreenContent(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit
) {
    val dimensions = LocalDimensions.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(dimensions.spaceMedium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.eshoplogo),
            contentDescription = "something",
            modifier = Modifier.size(dimensions.logoSize)
        )
        Text(
            text = stringResource(id = com.eshop.login_presentation.R.string.login_to_continue),
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = dimensions.font_20,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimensions.spaceLarge))
        if (state.errorMessageId != null) {
            ErrorBox(
                errorMessageId = state.errorMessageId,
                modifier = Modifier.padding(horizontal = dimensions.spaceMedium)
            )
            Spacer(modifier = Modifier.height(dimensions.spaceLarge))
        }
        InputField(
            inputText = state.credentials,
            onTextChange = {
                onEvent(LoginEvent.OnIdentifierEnter(it))
            },
            placeholderId = com.eshop.login_presentation.R.string.enter_email_or_username,
            modifier = Modifier.padding(horizontal = dimensions.spaceMedium),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(dimensions.spaceMedium))
        InputField(
            inputText = state.password,
            onTextChange = {
                onEvent(LoginEvent.OnPasswordEnter(it))
            },
            placeholderId = com.eshop.login_presentation.R.string.enter_password,
            trailingIconId = if (!state.isPasswordVisible) R.drawable.visibility_off_24 else R.drawable.visibility_24,
            onTrailingIconClick = {
                onEvent(LoginEvent.OnPasswordVisibilityIconClick)
            },
            modifier = Modifier.padding(horizontal = dimensions.spaceMedium),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (!state.isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None
        )
        Spacer(modifier = Modifier.height(dimensions.spaceMedium))
        EShopButton(
            content = {
                if (!state.isLoading) {
                    Text(
                        text = stringResource(id = R.string.login),
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = dimensions.font_16
                    )
                } else {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.size(dimensions.size_32)
                    )
                }
            },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            onButtonClick = {
                onEvent(LoginEvent.OnLoginClick)
            },
            modifier = Modifier
                .fillMaxWidth(0.4f),
            enabled = !state.isLoading
        )
        Text(text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colors.onBackground,
                    fontFamily = PoppinsFontFamily
                )
            ) {
                append(stringResource(id = com.eshop.login_presentation.R.string.forgot_password))
            }
            append(stringResource(id = com.eshop.login_presentation.R.string.space))
            withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                append(stringResource(id = com.eshop.login_presentation.R.string.reset_here))
            }
        }, modifier = Modifier.padding(vertical = dimensions.spaceSmall))
        Spacer(modifier = Modifier.weight(1f))
        ClickableText(text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colors.onBackground,
                    fontFamily = PoppinsFontFamily,
                    fontSize = dimensions.font_16
                )
            ) {
                append(stringResource(id = com.eshop.login_presentation.R.string.no_account))
            }
            append(stringResource(id = com.eshop.login_presentation.R.string.space))
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colors.primary, fontFamily = PoppinsFontFamily,
                    fontSize = dimensions.font_16
                )
            ) {
                append(stringResource(id = com.eshop.login_presentation.R.string.create_one))
            }
        }, modifier = Modifier.padding(vertical = dimensions.spaceSmall), onClick = {
            if (it > 12) {
                onEvent(LoginEvent.OnCreateOneClick)
            }
        })
    }
}

@Composable
@Preview
private fun LoginScreenPreview() {
    EShopTheme {
        LoginScreenContent(
            state = LoginState(),
            onEvent = {}
        )
    }
}

