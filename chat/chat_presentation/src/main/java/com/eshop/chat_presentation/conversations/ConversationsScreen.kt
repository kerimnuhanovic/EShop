package com.eshop.chat_presentation.conversations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.eshop.chat_domain.model.Conversation
import com.eshop.chat_domain.model.Message
import com.eshop.chat_presentation.components.ConversationsScreenLoadingSkeleton
import com.eshop.chat_presentation.components.ConversationItem
import com.eshop.core.util.UserType
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.R
import com.eshop.coreui.components.BottomBar
import com.eshop.coreui.components.InputField
import com.eshop.coreui.navigation.Route
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.util.BottomBarItem
import com.eshop.coreui.util.UiEvent
import com.eshop.coreui.util.generateBottomBarItems
import java.time.LocalDate

@Composable
fun ConversationsScreen(
    viewModel: ConversationsViewModel = hiltViewModel(),
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

    ConversationsScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        onNavigate = onNavigate
    )

}

@Composable
private fun ConversationsScreenContent(
    state: ConversationsState,
    onEvent: (ConversationsEvent) -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val dimensions = LocalDimensions.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            BottomBar(
                items = state.bottomBarItems,
                isBottomBarOverlapped = false,
                onNavigate = onNavigate,
                currentDestination = Route.CONVERSATIONS,
            )
        }) {
        if (state.isLoading) {
            ConversationsScreenLoadingSkeleton()
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
                            onEvent(ConversationsEvent.OnSearchQueryEnter(query))
                        },
                        placeholderId = com.eshop.chat_presentation.R.string.search_your_conversations,
                        modifier = Modifier.padding(horizontal = dimensions.spaceMedium),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        )
                    )
                }
                items(state.filteredConversations) { conversation ->
                    ConversationItem(conversation = conversation, onClick = {
                        onEvent(ConversationsEvent.OnConversationClick(conversation.chatPartner))
                    })
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
private fun ConversationsScreenPreview() {
    EShopTheme {
        ConversationsScreenContent(state = ConversationsState(
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
            ),
            bottomBarItems = generateBottomBarItems(UserType.Shop.type)
        ), onEvent = {}, onNavigate = {})
    }
}