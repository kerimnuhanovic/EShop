package com.eshop.chat_presentation.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.eshop.chat_domain.model.Conversation
import com.eshop.chat_domain.model.Message
import com.eshop.chat_presentation.components.ChatBottomBar
import com.eshop.chat_presentation.components.ChatScreenLoadingSkeleton
import com.eshop.chat_presentation.components.ChatTopBar
import com.eshop.chat_presentation.components.MessageItem
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.util.UiEvent
import java.time.LocalDate

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    onNavigateBack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val chatListState = rememberLazyListState()

    LaunchedEffect(key1 = state.conversation) {
        if (state.conversation != null) {
            //chatListState.animateScrollToItem(state.conversation.messages.size - 1)
        }
        viewModel.uiEvent.collect { uiEvent ->
            if (uiEvent is UiEvent.Navigate) {
                onNavigate(uiEvent)
            } else if (uiEvent is UiEvent.NavigateBack) {
                onNavigateBack()
            }
        }
    }

    ChatScreenContent(state = state, chatListState = chatListState, onEvent = viewModel::onEvent)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChatScreenContent(
    state: ChatState,
    chatListState: LazyListState,
    onEvent: (ChatEvent) -> Unit
) {
    val dimensions = LocalDimensions.current
    if (state.isLoading) {
        ChatScreenLoadingSkeleton()
    } else {
        if (state.conversation != null) {
            Scaffold(
                topBar = {
                    ChatTopBar(conversation = state.conversation, onBackClick = {
                        onEvent(ChatEvent.OnBackClick)
                    })
                },
                bottomBar = {
                    ChatBottomBar(textValue = state.message, onTextChange = { message ->
                        onEvent(ChatEvent.OnMessageEnter(message))
                    }, onSendClick = {
                        onEvent(ChatEvent.OnSendMessageClick)
                    })
                },
                modifier = Modifier.background(MaterialTheme.colors.background)
            ) {
                LazyColumn(
                    state = chatListState,
                    reverseLayout = true,
                    verticalArrangement = Arrangement.spacedBy(dimensions.spaceSmall),
                    modifier = Modifier
                        .padding(
                            top = it.calculateTopPadding(),
                            bottom = it.calculateBottomPadding(),
                            start = dimensions.spaceMedium,
                            end = dimensions.spaceMedium
                        )
                ) {
                    item {
                        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
                    }
                    items(state.conversation.messages.reversed()) { message ->
                        MessageItem(message = message)
                    }
                    item {
                        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ChatScreenPreview() {
    EShopTheme {
        ChatScreenContent(
            onEvent = {}, chatListState = rememberLazyListState(), state = ChatState(
                conversation = Conversation(
                    chatPartner = "Inoma",
                    chatPartnerProfileImage = "",
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
        )
    }
}