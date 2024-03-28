package com.eshop.coreui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.R
import com.eshop.coreui.theme.EShopTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimarySearchBar(
    inputText: String,
    onTextChange: (String) -> Unit,
    onLeadingIconClick: () -> Unit,
    onTrailingIconClick: () -> Unit,
    isSingleLine: Boolean,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    placeholderId: Int,
    leadingIcon: @Composable (() -> Unit) = {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.clickable { onLeadingIconClick() }
        )
    },
    trailingIcon: @Composable (() -> Unit) = {
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.clickable { onTrailingIconClick() }
        )
    },
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
            color = MaterialTheme.colorScheme.onBackground
        ),
        singleLine = isSingleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = Brush.horizontalGradient(
            listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.primary
            )
        ),
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.onPrimary)
            .height(dimensions.size_56),
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
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
                },
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    top = dimensions.default,
                    bottom = dimensions.default,
                    start = dimensions.spaceExtraSmall
                ),
            )
        }
    )
}

@Composable
@Preview
private fun PrimarySearchBarPreview() {
    EShopTheme {
        PrimarySearchBar(
            inputText = "Kerim",
            onTextChange = {},
            onLeadingIconClick = {},
            onTrailingIconClick = {},
            isSingleLine = true,
            placeholderId = R.string.search,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default,
        )
    }
}