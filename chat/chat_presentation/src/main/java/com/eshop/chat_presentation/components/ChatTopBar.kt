package com.eshop.chat_presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.eshop.chat_domain.model.Conversation
import com.eshop.core.util.BASE_URL
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.theme.EShopTheme

@Composable
fun ChatTopBar(conversation: Conversation, onBackClick: () -> Unit) {
    val dimensions = LocalDimensions.current
    TopAppBar(title = {
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
                    .size(dimensions.size_40)
                    .clip(CircleShape)
            )
        }
        Text(
            text = conversation.chatPartner,
            fontFamily = PoppinsFontFamily,
            fontSize = dimensions.font_16,
            color = MaterialTheme.colors.onSecondary
        )
    },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        }
    )
}

@Composable
@Preview
private fun ChatTopBarPreview() {
    EShopTheme {
        ChatTopBar(
            conversation = Conversation(
                chatPartner = "Inoma",
                chatPartnerProfileImage = "",
                messages = listOf()
            ),
            onBackClick = {}
        )
    }
}