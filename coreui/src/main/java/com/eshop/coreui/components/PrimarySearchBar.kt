package com.eshop.coreui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.R
import com.eshop.coreui.theme.EShopTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PrimarySearchBar(
    inputText: String,
    onTextChange: (String) -> Unit,
    isSingleLine: Boolean,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    placeholderId: Int,
    leadingIcon: @Composable (() -> Unit) = {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null
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
            color = MaterialTheme.colors.onBackground
        ),
        singleLine = isSingleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = Brush.horizontalGradient(listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primary)),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(CornerSize(dimensions.largeCornerRadius)))
            .background(color = MaterialTheme.colors.onSecondary)
            .height(dimensions.size_40),
        decorationBox = { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = inputText,
                innerTextField = {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.CenterStart
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
                },
                leadingIcon = leadingIcon,
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
            isSingleLine = true,
            placeholderId = R.string.search,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default
        )
    }
}