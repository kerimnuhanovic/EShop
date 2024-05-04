package com.eshop.chat_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.eshop.chat_domain.model.Message
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.theme.EShopTheme
import java.time.LocalDate

@Composable
fun MessageItem(message: Message, modifier: Modifier = Modifier) {
    val dimensions = LocalDimensions.current
    val backgroundColor =
        if (message.isCurrentUserReceiver!!) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant
    val shape = if (message.isCurrentUserReceiver!!) RoundedCornerShape(
        topStart = dimensions.default,
        topEnd = dimensions.spaceMedium,
        bottomEnd = dimensions.default,
        bottomStart = dimensions.spaceMedium
    ) else RoundedCornerShape(
        topStart = dimensions.spaceMedium,
        topEnd = dimensions.default,
        bottomEnd = dimensions.spaceMedium,
        bottomStart = dimensions.default
    )
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = if (message.isCurrentUserReceiver!!) Arrangement.Start else Arrangement.End
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = shape
                )
                .padding(dimensions.spaceSmall)
        ) {
            Text(
                text = message.payload,
                fontFamily = PoppinsFontFamily,
                fontSize = dimensions.font_12,
                color = MaterialTheme.colors.onSecondary
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun MessageItemPreview() {
    val dimensions = LocalDimensions.current
    EShopTheme {
        MessageItem(
            message = Message(
                sentBy = "kerimn",
                receivedBy = "Inoma",
                payload = "Hello, I am interested in one of your products",
                dateCreated = LocalDate.now(),
                isSeen = false,
                isCurrentUserReceiver = false
            ),
            modifier = Modifier.padding(dimensions.spaceSmall)
        )
    }
}