package com.eshop.chat_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.eshop.chat_domain.model.Conversation
import com.eshop.chat_domain.model.Message
import com.eshop.core.util.BASE_URL
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.theme.EShopTheme
import java.time.LocalDate

@Composable
fun ConversationItem(
    conversation: Conversation,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current
    val lastMessage = conversation.messages.last()
    val isLastMessageNotRead =
        lastMessage.isCurrentUserReceiver == true && !lastMessage.isSeen
    val numberOfUnreadMessages = conversation.messages.count { message ->
        message.isCurrentUserReceiver == true && !message.isSeen
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensions.spaceMedium,
                vertical = dimensions.spaceSmall
            )
            .clickable { onClick() }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Card(
                shape = CircleShape,
                backgroundColor = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .padding(dimensions.spaceExtraSmall),
                elevation = dimensions.spaceExtraSmall
            ) {
                AsyncImage(
                    model = "$BASE_URL/${conversation.chatPartnerProfileImage}",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(dimensions.size_50)
                        .clip(CircleShape)
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = dimensions.spaceMedium)
                    .fillMaxWidth(0.7f)
            ) {
                Text(
                    text = conversation.chatPartner,
                    fontSize = dimensions.font_12,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = if (isLastMessageNotRead) FontWeight.SemiBold else FontWeight.Medium,
                    color = if (isLastMessageNotRead) Color.Black else Color.Black.copy(alpha = 0.6f)
                )
                Text(
                    text = conversation.messages.last().payload,
                    fontSize = dimensions.font_12,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = if (isLastMessageNotRead) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (isLastMessageNotRead) Color.Black else Color.Black.copy(alpha = 0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (numberOfUnreadMessages != 0) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(dimensions.size_24)
                        .background(MaterialTheme.colors.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = numberOfUnreadMessages.toString(),
                        fontFamily = PoppinsFontFamily,
                        fontSize = dimensions.font_14,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ConversationItemPreview() {
    EShopTheme {
        ConversationItem(
            conversation = Conversation(
                "Inoma",
                "",
                messages = listOf(
                    Message(
                        sentBy = "kerimn",
                        receivedBy = "Inoma",
                        payload = "Hi, I am interested in your productsHi, I am interested in your products",
                        dateCreated = LocalDate.now(),
                        isSeen = false,
                        isCurrentUserReceiver = true
                    )
                )
            )
        )
    }
}