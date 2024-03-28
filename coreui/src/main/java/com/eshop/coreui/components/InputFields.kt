package com.eshop.coreui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        colors = TextFieldDefaults.colors(
            //backgroundColor = MediumGray,
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