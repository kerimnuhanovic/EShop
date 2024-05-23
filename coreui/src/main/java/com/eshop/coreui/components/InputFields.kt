package com.eshop.coreui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.R
import com.eshop.coreui.theme.MediumGray


@Composable
fun InputField(
    inputText: String,
    onTextChange: (String) -> Unit,
    placeholderId: Int,
    modifier: Modifier = Modifier,
    trailingIconId: Int? = null,
    onTrailingIconClick: () -> Unit = {},
    isSingleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val dimensions = LocalDimensions.current
    TextField(
        value = inputText, onValueChange = onTextChange, trailingIcon = {
            if (trailingIconId != null) {
                Icon(
                    painter = painterResource(id = trailingIconId),
                    contentDescription = stringResource(
                        id = R.string.trailing_icon
                    ),
                    modifier = Modifier.clickable { onTrailingIconClick() }
                )
            }
        }, modifier = modifier
            .fillMaxWidth(), shape = RoundedCornerShape(CornerSize(dimensions.largeCornerRadius)),
        textStyle = TextStyle(
            fontFamily = PoppinsFontFamily,
            letterSpacing = dimensions.smallLetterSpacing
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MediumGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = stringResource(id = placeholderId),
                fontFamily = PoppinsFontFamily,
                fontSize = dimensions.font_12,
                letterSpacing = dimensions.smallLetterSpacing
            )
        },
        singleLine = isSingleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageInputField(
    inputText: String,
    onTextChange: (String) -> Unit,
    placeholderId: Int,
    isSingleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current

    BasicTextField(
        value = inputText,
        onValueChange = onTextChange,
        enabled = true,
        textStyle = TextStyle(
            fontFamily = PoppinsFontFamily,
            fontSize = dimensions.font_14,
            letterSpacing = dimensions.smallLetterSpacing,
            color = MaterialTheme.colors.onBackground
        ),
        singleLine = isSingleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = Brush.horizontalGradient(
            listOf(
                MaterialTheme.colors.primary,
                MaterialTheme.colors.primary
            )
        ),
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.onPrimary)
            .height(dimensions.size_50),
        decorationBox = { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = inputText,
                innerTextField = {
                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        innerTextField()
                    }
                },
                enabled = true,
                singleLine = isSingleLine,
                visualTransformation = VisualTransformation.None,
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    top = dimensions.default,
                    bottom = dimensions.default,
                    start = dimensions.spaceExtraSmall
                ),
                interactionSource = remember { MutableInteractionSource() },
                placeholder = {
                    if (inputText.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = placeholderId),
                                fontFamily = PoppinsFontFamily,
                                fontSize = dimensions.font_14,
                                letterSpacing = dimensions.smallLetterSpacing
                            )
                        }
                    }
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TextArea(
    inputText: String,
    onTextChange: (String) -> Unit,
    placeholderId: Int,
    isSingleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current

    BasicTextField(
        value = inputText,
        onValueChange = onTextChange,
        enabled = true,
        textStyle = TextStyle(
            fontFamily = PoppinsFontFamily,
            fontSize = dimensions.font_14,
            letterSpacing = dimensions.smallLetterSpacing,
            color = MaterialTheme.colors.onBackground
        ),
        singleLine = isSingleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = Brush.horizontalGradient(
            listOf(
                MaterialTheme.colors.primary,
                MaterialTheme.colors.primary
            )
        ),
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.onPrimary)
            .height(dimensions.size_200)
            .border(
                width = dimensions.size_1,
                color = MaterialTheme.colors.onBackground,
                shape = RoundedCornerShape(dimensions.spaceSmall)
            ),
        decorationBox = { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = inputText,
                innerTextField = {
                    Row(
                        modifier = Modifier.fillMaxHeight().padding(dimensions.spaceSmall)
                    ) {
                        innerTextField()
                    }
                },
                enabled = true,
                singleLine = isSingleLine,
                visualTransformation = VisualTransformation.None,
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    top = dimensions.default,
                    bottom = dimensions.default,
                    start = dimensions.spaceExtraSmall
                ),
                interactionSource = remember { MutableInteractionSource() },
                placeholder = {
                    if (inputText.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxHeight().padding(dimensions.spaceSmall)
                        ) {
                            Text(
                                text = stringResource(id = placeholderId),
                                fontFamily = PoppinsFontFamily,
                                fontSize = dimensions.font_14,
                                letterSpacing = dimensions.smallLetterSpacing
                            )
                        }
                    }
                }
            )
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun InputFieldPreview(
    inputText: String = "",
    onTextChange: (String) -> Unit = {},
    trailingIconId: Int = R.drawable.visibility_24
) {
    val dimensions = LocalDimensions.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensions.spaceMedium)
    ) {
        InputField(
            inputText = inputText,
            onTextChange = onTextChange,
            placeholderId = R.string.enter_your_email,
            trailingIconId = trailingIconId
        )
    }
}