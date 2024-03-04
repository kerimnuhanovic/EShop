package com.eshop.coreui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.R
import com.eshop.coreui.theme.EShopTheme

@Composable
fun PrimaryTopBanner(
    iconId: Int,
    inputText: String,
    onTextChange: (String) -> Unit,
    isSingleLine: Boolean,
    placeholderId: Int,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current
    Card(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background,
        elevation = dimensions.spaceSmall
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = dimensions.spaceExtraSmall)
        ) {
            Image(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier.size(dimensions.size_60)
            )
            PrimarySearchBar(
                inputText = inputText,
                onTextChange = onTextChange,
                isSingleLine = isSingleLine,
                placeholderId = placeholderId,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                modifier = Modifier.padding(horizontal = dimensions.spaceExtraSmall),
                onTrailingIconClick = {},
                onLeadingIconClick = {}
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PrimaryTopBannerPreview() {
    EShopTheme {
        PrimaryTopBanner(
            iconId = R.drawable.eshoplogo,
            inputText = "",
            onTextChange = {},
            isSingleLine = true,
            placeholderId = R.string.search,
        )
    }
}