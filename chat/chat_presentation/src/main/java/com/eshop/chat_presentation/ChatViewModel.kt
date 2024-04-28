package com.eshop.chat_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshop.chat_domain.model.Conversation
import com.eshop.chat_domain.model.Message
import com.eshop.chat_domain.usecase.ConnectToSocketUseCase
import com.eshop.chat_domain.usecase.FetchUserConversationsUseCase
import com.eshop.chat_domain.usecase.ReceiveNewMessageUseCase
import com.eshop.chat_domain.usecase.SendMessageUseCase
import com.eshop.core.util.Result
import com.eshop.coreui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val connectToSocketUseCase: ConnectToSocketUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val fetchUserConversationsUseCase: FetchUserConversationsUseCase,
    private val receiveNewMessageUseCase: ReceiveNewMessageUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<ChatState> = MutableStateFlow(ChatState(isLoading = true))
    val state: StateFlow<ChatState> = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            when (val result = fetchUserConversationsUseCase()) {
                is Result.Success -> {
                    connectToSocketUseCase()
                    _state.value = state.value.copy(
                        conversations = result.data,
                        filteredConversations = result.data,
                        isLoading = false
                    )
                    receiveNewMessages()
                }

                is Result.Failure -> {

                }
            }
        }
    }

    private fun receiveNewMessages() {
        viewModelScope.launch {
            receiveNewMessageUseCase().collect { message ->
                if (message != null) {
                    val conversations = state.value.conversations.map { conversation ->
                        Conversation(
                            chatPartner = conversation.chatPartner,
                            chatPartnerProfileImage = conversation.chatPartnerProfileImage,
                            messages = if (conversation.chatPartner == message.sentBy) conversation.messages.plus(message) else conversation.messages
                        )
                    }
                    _state.value = state.value.copy(
                        conversations = conversations,
                        filteredConversations = conversations
                    )
                }
            }
        }
    }

    fun onEvent(event: ChatEvent) {
        when (event) {
            ChatEvent.OnMessageSend -> {
                sendMessage()
            }

            is ChatEvent.OnSearchQueryEnter -> {
                _state.value = state.value.copy(
                    searchQuery = event.query,
                    filteredConversations = state.value.conversations.filter { conversation ->
                        conversation.chatPartner.lowercase().contains(event.query.lowercase())
                    }
                )
            }
        }
    }

    private fun sendMessage() {
        viewModelScope.launch {
            sendMessageUseCase.invoke("knuhanovic", "${Math.random()}")
        }
    }
}