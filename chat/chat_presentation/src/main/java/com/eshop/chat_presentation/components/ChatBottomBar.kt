package com.eshop.chat_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.eshop.chat_presentation.R
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.components.InputField
import com.eshop.coreui.components.MessageInputField
import com.eshop.coreui.theme.EShopTheme


@Composable
fun ChatBottomBar(
    textValue: String,
    onTextChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    val dimensions = LocalDimensions.current
    BottomAppBar(backgroundColor = MaterialTheme.colors.background, elevation = dimensions.spaceSmall) {
        Spacer(modifier = Modifier.width(dimensions.spaceSmall))
        MessageInputField(
            inputText = textValue,
            onTextChange = onTextChange,
            placeholderId = R.string.message,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = dimensions.spaceSmall)
                .clip(RoundedCornerShape(dimensions.largeCornerRadius))
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = { onSendClick() },
            modifier = Modifier
                .padding(end = dimensions.spaceExtraSmall)
                .clip(CircleShape)
                .background(MaterialTheme.colors.secondary)
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier.size(dimensions.size_20)
            )
        }
    }
}

@Composable
@Preview
private fun ChatBottomBarPreview() {
    EShopTheme {
        ChatBottomBar(textValue = "", onTextChange = {}, onSendClick = {})
    }
}