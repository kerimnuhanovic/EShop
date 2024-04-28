package com.eshop.chat_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import com.eshop.chat_domain.model.Conversation
import com.eshop.chat_domain.model.Message
import com.eshop.chat_presentation.components.ChatScreenLoadingSkeleton
import com.eshop.chat_presentation.components.ConversationItem
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.R
import com.eshop.coreui.components.BottomBar
import com.eshop.coreui.components.InputField
import com.eshop.coreui.navigation.Route
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.util.BottomBarItem
import com.eshop.coreui.util.UiEvent
import java.time.LocalDate
import kotlin.math.roundToInt

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            if (uiEvent is UiEvent.Navigate) {
                onNavigate(uiEvent)
            }
        }
    }

    ChatScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        onNavigate = onNavigate
    )

}

@Composable
private fun ChatScreenContent(
    state: ChatState,
    onEvent: (ChatEvent) -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val dimensions = LocalDimensions.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            BottomBar(
                items = listOf(
                    BottomBarItem(
                        text = "Products",
                        icon = Icons.Rounded.List,
                        route = Route.PRODUCTS_OVERVIEW
                    ),
                    BottomBarItem(
                        text = "Shops",
                        iconId = R.drawable.shopping_basket_24,
                        route = Route.SHOPS_OVERVIEW
                    ),
                    BottomBarItem(
                        text = "Message",
                        iconId = R.drawable.message_24,
                        route = Route.CHAT
                    ),
                    BottomBarItem(
                        text = "Basket",
                        icon = Icons.Rounded.ShoppingCart,
                        route = Route.BASKET
                    ),
                    BottomBarItem(
                        text = "Orders",
                        icon = Icons.Rounded.Settings,
                        route = Route.ORDERS
                    )
                ),
                isBottomBarOverlapped = false,
                onNavigate = onNavigate,
                currentDestination = Route.CHAT,
            )
        }) {
        if (state.isLoading) {
            ChatScreenLoadingSkeleton()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(it)
            ) {
                item {
                    Text(
                        text = stringResource(id = com.eshop.chat_presentation.R.string.conversations),
                        fontFamily = PoppinsFontFamily,
                        fontSize = dimensions.font_20,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = dimensions.spaceMedium),
                        color = MaterialTheme.colors.primary
                    )
                }
                item {
                    InputField(
                        inputText = state.searchQuery,
                        onTextChange = { query ->
                            onEvent(ChatEvent.OnSearchQueryEnter(query))
                        },
                        placeholderId = com.eshop.chat_presentation.R.string.search_your_conversations,
                        modifier = Modifier.padding(horizontal = dimensions.spaceMedium),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        )
                    )
                }
                items(state.filteredConversations) { conversation ->
                    ConversationItem(conversation = conversation)
                    Divider()
                }
                if (state.filteredConversations.isEmpty()) {
                    item {
                        Text(
                            text = stringResource(id = com.eshop.chat_presentation.R.string.no_matching_conversations),
                            fontFamily = PoppinsFontFamily,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = dimensions.font_16,
                            modifier = Modifier.padding(dimensions.spaceMedium)
                        )
                    }
                }
            }
        }
    }

}

@Composable
@Preview
private fun ChatScreenPreview() {
    EShopTheme {
        ChatScreenContent(state = ChatState(
            conversations = listOf(
                Conversation(
                    chatPartner = "Inoma",
                    chatPartnerProfileImage = "profileImage",
                    messages = listOf(
                        Message(
                            sentBy = "kerimn",
                            receivedBy = "Inoma",
                            payload = "Hello, I want to order one of your product.",
                            dateCreated = LocalDate.now(),
                            isSeen = false,
                            isCurrentUserReceiver = true
                        )
                    )
                )
            )
        ), onEvent = {}, onNavigate = {})
    }
}