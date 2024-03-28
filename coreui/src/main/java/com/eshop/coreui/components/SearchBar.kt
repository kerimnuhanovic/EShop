package com.eshop.coreui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.R
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.theme.MediumGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    inputText: String,
    onTextChange: (String) -> Unit,
    onIconClick: () -> Unit,
    isSingleLine: Boolean,
    placeholderId: Int,
    isExpanded: Boolean,
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current
    val animateTextFieldWidth = animateDpAsState(
        targetValue = if (isExpanded) LocalConfiguration.current.screenWidthDp.dp else dimensions.default,
        animationSpec = tween(1000)
    )
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = modifier
            .height(dimensions.size_55)
            .fillMaxWidth()
    ) {
        if (animateTextFieldWidth.value > dimensions.size_55) {
            TextField(
                value = inputText,
                onValueChange = onTextChange,
                shape = RoundedCornerShape(CornerSize(dimensions.largeCornerRadius)),
                textStyle = TextStyle(
                    fontFamily = PoppinsFontFamily,
                    fontSize = dimensions.font_14,
                    letterSpacing = dimensions.smallLetterSpacing
                ),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MediumGray,
                    focusedContainerColor = MediumGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = placeholderId),
                        fontFamily = PoppinsFontFamily,
                        fontSize = dimensions.font_14,
                        letterSpacing = dimensions.smallLetterSpacing
                    )
                },
                singleLine = isSingleLine,
                modifier = Modifier
                    .width(animateTextFieldWidth.value)
            )
        }
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(dimensions.size_55)
                .clickable { onIconClick() }
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.padding(dimensions.spaceSmall)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SearchBarPreview() {
    EShopTheme {
        SearchBar(
            "",
            {},
            {},
            true,
            R.string.search,
            true
        )
    }
}